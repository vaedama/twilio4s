package org.aedama.twilio4s.http

import akka.http.scaladsl.model.{HttpResponse, StatusCode}
import io.circe.Json

/**
  * Base class for Twilio exceptions
  *
  * @param status  HTTP status code
  * @param message exception message
  */
class TwilioException(status: StatusCode, message: String) extends Exception(s"[$status] $message")

/**
  * This is thrown when Twilio sends a 401 or 403
  */
final case class AuthenticationException(message: String, status: StatusCode = 401)
    extends TwilioException(status, message)

/**
  * This is thrown when Twilio sends a 500
  */
final case class TwilioInternalServerError(responseJson: Json, status: Int = 500)
    extends TwilioException(status, s"Twilio server error: response=$responseJson")

/**
  * This is thrown when Twilio sends a 503
  */
final case class TwilioServiceUnavailable(responseJson: Json, status: Int = 503)
    extends TwilioException(status, s"Twilio service unavailable: response=$responseJson")

/**
  * This is thrown when Twilio error response code is unhandled
  */
final case class UnhandledServerError(httpResponse: HttpResponse)
    extends TwilioException(httpResponse.status.intValue, s"Unhandled server error: response=$httpResponse")
