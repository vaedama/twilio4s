package org.aedama.twilio4s.v1.voice.request

import enumeratum.{Enum, EnumEntry}
import io.circe.{Decoder, Encoder}

import scala.collection.immutable.IndexedSeq

/**
  * @see https://www.twilio.com/docs/voice/api/call#statuscallbackevent
  *
  *      The call progress events that we will send to the status_callback URL.
  */
sealed abstract class StatusCallbackEvent(val value: String) extends EnumEntry {
  override val entryName: String = value
}

object StatusCallbackEvent extends Enum[StatusCallbackEvent] {
  val values: IndexedSeq[StatusCallbackEvent] = findValues

  case object Initiated extends StatusCallbackEvent("initiated")

  case object Ringing extends StatusCallbackEvent("ringing")

  case object Answered extends StatusCallbackEvent("answered")

  case object Completed extends StatusCallbackEvent("completed")

  implicit val statusCallbackEventDecoder: Decoder[StatusCallbackEvent] = enumeratum.Circe.decoder(StatusCallbackEvent)
  implicit val statusCallbackEventEncoder: Encoder[StatusCallbackEvent] = enumeratum.Circe.encoder(StatusCallbackEvent)
}
