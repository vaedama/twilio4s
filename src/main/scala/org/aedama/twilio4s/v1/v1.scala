package org.aedama.twilio4s

import akka.http.scaladsl.HttpExt
import akka.http.scaladsl.model.{ContentTypes, FormData, HttpMethods, HttpRequest}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.Materializer
import com.typesafe.scalalogging.Logger
import de.knutwalker.akka.http.support.CirceHttpSupport._
import de.knutwalker.akka.stream.support.CirceStreamSupport
import io.circe.{Decoder, Json}
import org.aedama.twilio4s.http.Errors.Error
import org.aedama.twilio4s.http.{
  JsonDecoderException,
  TwilioInternalServerError,
  TwilioServiceUnavailable,
  UnhandledServerError
}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

package object v1 {

  /**
    * A helper function which fires a single HTTP GET request using akka-http
    * and decodes the JSON response using the decoder
    *
    * @param url The URL for the request
    */
  private[v1] def httpGET[A](url: String)(implicit apiKey: ApiKey,
                                          httpClient: HttpExt,
                                          executionContext: ExecutionContext,
                                          materializer: Materializer,
                                          logger: Logger,
                                          decoder: Decoder[A]): Future[Either[Error, Try[A]]] =
    execute(HttpRequest(uri = url, method = HttpMethods.GET, headers = List(apiKey.toBasicAuthHeader)))

  /**
    * A helper function which fires a single HTTP POST request using akka-http
    * and decodes the JSON response using the decoder
    *
    * @param url        The URL for the request
    * @param formParams POST form parameters
    */
  private[v1] def httpPOST[A](url: String, formParams: Map[String, String])(
      implicit httpClient: HttpExt,
      executionContext: ExecutionContext,
      materializer: Materializer,
      logger: Logger,
      apiKey: ApiKey,
      decoder: Decoder[A]): Future[Either[Error, Try[A]]] =
    execute(
      HttpRequest(uri = url,
                  method = HttpMethods.POST,
                  headers = List(apiKey.toBasicAuthHeader),
                  entity = FormData(formParams).toEntity))

  private def execute[A](request: HttpRequest)(implicit httpClient: HttpExt,
                                               executionContext: ExecutionContext,
                                               materializer: Materializer,
                                               logger: Logger,
                                               decoder: Decoder[A]): Future[Either[Error, Try[A]]] =
    httpClient.singleRequest(request).flatMap { response =>
      logger.info(s"request=$request received response=$response")

      val entity = response.entity.httpEntity

      if (response.status.isSuccess) {
        Unmarshal(entity.withContentType(ContentTypes.`application/json`))
          .to[A]
          .map(a => Right(Success(a)))
          .recover { case e: CirceStreamSupport.JsonParsingException => Right(Failure(e)) }
      } else {
        Unmarshal(entity.withContentType(ContentTypes.`application/json`))
          .to[Json]
          .map { json =>
            response.status.intValue match {
              case code if 400 to 499 contains code =>
                val eitherDecodingFailureOrError = code match {
                  case 400 => Decoder.instance(_.as[Error.BadRequest])(json.hcursor)
                  case 401 => Decoder.instance(_.as[Error.Unauthorized])(json.hcursor)
                  case 403 => Decoder.instance(_.as[Error.Forbidden])(json.hcursor)
                  case 404 => Decoder.instance(_.as[Error.NotFound])(json.hcursor)
                  case 405 => Decoder.instance(_.as[Error.MethodNotAllowed])(json.hcursor)
                  case 429 => Decoder.instance(_.as[Error.TooManyRequests])(json.hcursor)
                }
                eitherDecodingFailureOrError.fold(
                  decodingFailure => throw JsonDecoderException(decodingFailure, request, json, code),
                  error => Left(error)
                )
              case err =>
                response.discardEntityBytes()
                if (err == 500) throw TwilioInternalServerError(json)
                else if (err == 503) throw TwilioServiceUnavailable(json)
                else throw UnhandledServerError(response)
            }
          }
      }
    }

}
