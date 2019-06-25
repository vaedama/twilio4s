package org.aedama.twilio4s.v1.voice.request

import enumeratum.{Enum, EnumEntry}
import io.circe.{Decoder, Encoder}

import scala.collection.immutable.IndexedSeq

/**
  * @see https://www.twilio.com/docs/voice/api/call#recordingstatuscallbackevent
  *
  *      The recording status events that will trigger calls to the
  *      URL specified in `recordingStatusCallback`.
  */
sealed abstract class RecordingStatusCallbackEvent(val value: String) extends EnumEntry {
  override val entryName: String = value
}

object RecordingStatusCallbackEvent extends Enum[RecordingStatusCallbackEvent] {
  val values: IndexedSeq[RecordingStatusCallbackEvent] = findValues

  case object InProgress extends RecordingStatusCallbackEvent("in-progress")

  case object Completed extends RecordingStatusCallbackEvent("completed")

  case object Absent extends RecordingStatusCallbackEvent("absent")

  implicit val recordingStatusCallbackEventDecoder: Decoder[RecordingStatusCallbackEvent] =
    enumeratum.Circe.decoder(RecordingStatusCallbackEvent)

  implicit val recordingStatusCallbackEventEncoder: Encoder[RecordingStatusCallbackEvent] =
    enumeratum.Circe.encoder(RecordingStatusCallbackEvent)
}
