package org.aedama.twilio4s.v1.voice.response

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}

final case class SubResourceUris(notifications: String,
                                 recordings: String,
                                 feedback: String,
                                 feedback_summaries: String)

object SubResourceUris {

  implicit val subResourceUrisDecoder: Decoder[SubResourceUris] = deriveDecoder[SubResourceUris]
  implicit val subResourceUrisEncoder: Encoder[SubResourceUris] = deriveEncoder[SubResourceUris]

}
