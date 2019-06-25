package org.aedama.twilio4s.http

import akka.http.scaladsl.model.HttpRequest
import io.circe.{DecodingFailure, Json}

case class JsonDecoderException(
    failure: DecodingFailure,
    request: HttpRequest,
    responseJson: Json,
    status: Int
) extends Exception {
  override def getMessage = s"JSON decoder failed: failure=$failure"
}
