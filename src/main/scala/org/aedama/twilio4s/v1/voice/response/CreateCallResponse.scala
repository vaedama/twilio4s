package org.aedama.twilio4s.v1.voice.response

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.jawn.decode
import io.circe.{Decoder, Encoder, Error}

/**
  * Represents Twilio Call
  *
  * @see https://www.twilio.com/docs/voice/api/call#call-properties
  */
final case class CreateCallResponse(account_sid: String,
                                    annotation: Option[String],
                                    answered_by: Option[String],
                                    api_version: String,
                                    caller_name: Option[String],
                                    date_created: Option[String],
                                    date_updated: Option[String],
                                    direction: String,
                                    duration: Option[String],
                                    end_time: Option[String],
                                    forwarded_from: Option[String],
                                    from: String,
                                    from_formatted: String,
                                    group_sid: Option[String],
                                    parent_call_sid: Option[String],
                                    phone_number_sid: String,
                                    price: Option[Double],
                                    price_unit: String,
                                    sid: String,
                                    start_time: Option[String],
                                    status: CallStatus,
                                    subresource_uris: SubResourceUris,
                                    to: String,
                                    to_formatted: String,
                                    uri: String)

object CreateCallResponse {

  implicit val callDecoder: Decoder[CreateCallResponse] = deriveDecoder[CreateCallResponse]
  implicit val callEncoder: Encoder[CreateCallResponse] = deriveEncoder[CreateCallResponse]

  def parseJson(json: String): Either[Error, CreateCallResponse] = decode[CreateCallResponse](json)

}
