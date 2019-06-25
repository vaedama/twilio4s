package org.aedama.twilio4s.v1

import akka.http.scaladsl.HttpExt
import akka.http.scaladsl.model._
import akka.stream.Materializer
import akka.util.ByteString
import com.typesafe.scalalogging.Logger
import org.aedama.twilio4s.http._
import org.aedama.twilio4s.util.Configuration
import org.slf4j.LoggerFactory

import scala.concurrent.duration.{FiniteDuration, _}
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.xml.XML

object Twilio {

  private implicit val logger: Logger = Logger(LoggerFactory.getLogger(getClass.getName))

  def init(timeout: FiniteDuration = 5.seconds)(
      implicit apiKey: ApiKey,
      httpClient: HttpExt,
      executionContext: ExecutionContext,
      materializer: Materializer): Either[AuthenticationException, HttpResponse] =
    Await.result(initAsync, timeout)

  def initAsync(implicit apiKey: ApiKey,
                httpClient: HttpExt,
                executionContext: ExecutionContext,
                materializer: Materializer): Future[Either[AuthenticationException, HttpResponse]] =
    httpClient
      .singleRequest(
        HttpRequest(uri = Configuration.baseUrl, method = HttpMethods.GET, headers = List(apiKey.toBasicAuthHeader)))
      .flatMap {
        case response @ HttpResponse(StatusCodes.OK, _, entity, _) =>
          contentFuture(entity).map { content =>
            logger.debug({
              val elem         = XML.loadString(content)
              val account      = elem \ "Accounts" \ "Account"
              val status       = account \ "Status"
              val friendlyName = account \ "FriendlyName"
              s"Authentication successful: account_name=${friendlyName.text} account_status=${status.text}"
            })
            Right(response)
          }
        case response =>
          contentFuture(response.entity).map { message =>
            logger.error(s"Authentication failed: status=${response.status} message=$message")
            Left(AuthenticationException(message, response.status))
          }
      }

  private def contentFuture(entity: ResponseEntity)(implicit executionContext: ExecutionContext,
                                                    materializer: Materializer): Future[String] =
    entity.httpEntity
      .withContentType(ContentTypes.`text/xml(UTF-8)`)
      .dataBytes
      .runFold(ByteString(""))(_ ++ _)
      .map(_.utf8String)

}
