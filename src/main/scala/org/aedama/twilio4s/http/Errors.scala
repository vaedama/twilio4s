package org.aedama.twilio4s.http

import io.circe.Decoder.Result
import io.circe.{Decoder, Encoder, HCursor}

object Errors {

  /**
    * Typed errors from Twilio
    *
    * @see https://www.twilio.com/docs/verify/return-and-error-codes#return-codes,
    *      https://www.twilio.com/docs/verify/return-and-error-codes#error-codes
    *      https://www.twilio.com/docs/api/errors and
    *      https://www.twilio.com/docs/usage/your-request-to-twilio
    * @param status    HTTP status code
    * @param code      Twilio error code
    * @param message   error message
    * @param more_info Twilio docs link to understand more information about the error
    */
  sealed abstract class Error(
      val status: Int,
      val code: Int,
      val message: String,
      val more_info: String
  ) extends Exception {
    override def toString: String = s"""Error($status, $code, $message, $more_info)"""

    override def getMessage: String = toString
  }

  object Error {

    final case class BadRequest(
        override val code: Int,
        override val message: String,
        override val more_info: String
    ) extends Error(400, code, message, more_info)

    final case class Unauthorized(
        override val code: Int,
        override val message: String,
        override val more_info: String
    ) extends Error(401, code, message, more_info)

    final case class Forbidden(
        override val code: Int,
        override val message: String,
        override val more_info: String
    ) extends Error(403, code, message, more_info)

    final case class NotFound(
        override val code: Int,
        override val message: String,
        override val more_info: String
    ) extends Error(404, code, message, more_info)

    final case class MethodNotAllowed(
        override val code: Int,
        override val message: String,
        override val more_info: String
    ) extends Error(405, code, message, more_info)

    final case class TooManyRequests(
        override val code: Int,
        override val message: String,
        override val more_info: String
    ) extends Error(429, code, message, more_info)

  }

  private def errorDecoder(c: HCursor): (Result[Int], Result[String], Result[String]) =
    (c.downField("code").as[Int], c.downField("message").as[String], c.downField("more_info").as[String])

  import cats.instances.either._
  import cats.syntax.apply._

  implicit val badRequestDecoder: Decoder[Error.BadRequest] =
    Decoder.instance[Error.BadRequest](errorDecoder(_).mapN(Error.BadRequest.apply))
  implicit val unauthorizedDecoder: Decoder[Error.Unauthorized] =
    Decoder.instance[Error.Unauthorized](errorDecoder(_).mapN(Error.Unauthorized.apply))
  implicit val forbiddenDecoder: Decoder[Error.Forbidden] =
    Decoder.instance[Error.Forbidden](errorDecoder(_).mapN(Error.Forbidden.apply))
  implicit val notFoundDecoder: Decoder[Error.NotFound] =
    Decoder.instance[Error.NotFound](errorDecoder(_).mapN(Error.NotFound.apply))
  implicit val methodNotAllowedDecoder: Decoder[Error.MethodNotAllowed] =
    Decoder.instance[Error.MethodNotAllowed](errorDecoder(_).mapN(Error.MethodNotAllowed.apply))
  implicit val tooManyRequestsDecoder: Decoder[Error.TooManyRequests] =
    Decoder.instance[Error.TooManyRequests](errorDecoder(_).mapN(Error.TooManyRequests.apply))

  private def errorEncoder: Encoder[Error] =
    Encoder.forProduct3(
      "code",
      "message",
      "more_info"
    )(e => (e.code, e.message, e.more_info))

  implicit val badRequestEncoder: Encoder[Error.BadRequest] =
    Encoder.instance[Error.BadRequest](e => errorEncoder(e))
  implicit val unauthorizedEncoder: Encoder[Error.Unauthorized] =
    Encoder.instance[Error.Unauthorized](e => errorEncoder(e))
  implicit val forbiddenEncoder: Encoder[Error.Forbidden] =
    Encoder.instance[Error.Forbidden](e => errorEncoder(e))
  implicit val notFoundEncoder: Encoder[Error.NotFound] =
    Encoder.instance[Error.NotFound](e => errorEncoder(e))
  implicit val methodNotAllowedEncoder: Encoder[Error.MethodNotAllowed] =
    Encoder.instance[Error.MethodNotAllowed](e => errorEncoder(e))
  implicit val tooManyRequestsEncoder: Encoder[Error.TooManyRequests] =
    Encoder.instance[Error.TooManyRequests](e => errorEncoder(e))

}
