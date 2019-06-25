package org.aedama.twilio4s.v1.voice

import java.net.URI

import akka.http.scaladsl.HttpExt
import akka.stream.Materializer
import com.typesafe.scalalogging.Logger
import org.aedama.twilio4s.http.{Errors, HttpMethod}
import org.aedama.twilio4s.util.Configuration
import org.aedama.twilio4s.v1.{ApiKey, _}
import org.aedama.twilio4s.v1.`type`.Endpoint
import org.aedama.twilio4s.v1.voice.request.{CreateExtraOptions, CreateFallbackOptions}
import org.aedama.twilio4s.v1.voice.response.CreateCallResponse
import org.slf4j.LoggerFactory

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

/**
  * Programmable voice operations
  */
trait VoiceClient {

  object Call {

    private val endpoint: String = s"${Configuration.accountUrl}/Calls.json"

    private implicit val logger: Logger = Logger(LoggerFactory.getLogger(getClass.getName))

    /**
      * Executes create call
      *
      * @param to               Phone number, SIP address, or client identifier to call
      * @param from             Twilio number from which to originate the call
      * @param url              The absolute URL that returns TwiML for this call
      * @param httpClient       Akka extension for HTTP
      * @param executionContext To execute create call asynchronously
      * @param materializer     To make the streams run
      * @param apiKey           Twilio API key
      * @return Result of creating the call resource
      */
    def create(to: Endpoint, from: Endpoint, url: URI)(
        implicit apiKey: ApiKey,
        httpClient: HttpExt,
        executionContext: ExecutionContext,
        materializer: Materializer): Future[Either[Errors.Error, Try[CreateCallResponse]]] =
      httpPOST[CreateCallResponse](endpoint, formParams(to, from, url).toMap)

    /**
      * Executes create call with additional options
      *
      * @param to                    Phone number, SIP address, or client identifier to call
      * @param from                  Twilio number from which to originate the call
      * @param url                   The absolute URL that returns TwiML for this call
      * @param method                The HTTP method we should use when calling the url parameter's value.
      *                              Can be: GET or POST and the default is POST.
      * @param createFallbackOptions Fallback options in case of error
      * @param createExtraOptions    Additional options to execute a call
      * @param httpClient            Akka extension for HTTP
      * @param executionContext      To execute create call asynchronously
      * @param materializer          To make the streams run
      * @param apiKey                Twilio API key
      * @return Result of creating the call resource
      */
    def create(to: Endpoint,
               from: Endpoint,
               url: URI,
               method: HttpMethod,
               createFallbackOptions: Option[CreateFallbackOptions],
               createExtraOptions: Option[CreateExtraOptions])(
        implicit apiKey: ApiKey,
        httpClient: HttpExt,
        executionContext: ExecutionContext,
        materializer: Materializer): Future[Either[Errors.Error, Try[CreateCallResponse]]] =
      httpPOST[CreateCallResponse](endpoint, formParams(to, from, url, method).toMap ++ {
        createFallbackOptions.map(_.toPostParams) ++ createExtraOptions.map(_.toPostParams)
      }.flatten.toMap)

    private def formParams(to: Endpoint, from: Endpoint, url: URI, method: HttpMethod = HttpMethod.POST)(
        implicit apiKey: ApiKey): Seq[(String, String)] =
      Seq("AccountSid" -> apiKey.accountSID,
          "To"         -> to.getEndpoint,
          "From"       -> from.getEndpoint,
          "Url"        -> url.toString,
          "Method"     -> method.entryName)

    /**
      * Executes create call with using application SID and additional options
      *
      * @param to             Phone number, SIP address, or client identifier to call
      * @param from           Twilio number from which to originate the call
      * @param applicationSid The SID of the Application resource that will handle the call
      * @return Execution result of creation
      */
    def create(to: Endpoint, from: Endpoint, applicationSid: String, createExtraOptions: Option[CreateExtraOptions])(
        implicit apiKey: ApiKey,
        httpClient: HttpExt,
        executionContext: ExecutionContext,
        materializer: Materializer): Future[Either[Errors.Error, Try[CreateCallResponse]]] =
      httpPOST[CreateCallResponse](endpoint,
                                   formParamsForAppSid(to, from, applicationSid).toMap ++
                                     createExtraOptions.fold(Map.empty[String, String])(_.toPostParams))

    private def formParamsForAppSid(to: Endpoint, from: Endpoint, applicationSid: String)(
        implicit apiKey: ApiKey): Seq[(String, String)] =
      Seq("AccountSid"     -> apiKey.accountSID,
          "To"             -> to.getEndpoint,
          "From"           -> from.getEndpoint,
          "ApplicationSid" -> applicationSid)
  }

}
