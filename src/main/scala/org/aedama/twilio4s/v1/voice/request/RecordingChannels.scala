package org.aedama.twilio4s.v1.voice.request

import enumeratum.{Enum, EnumEntry}
import io.circe.{Decoder, Encoder}

import scala.collection.immutable.IndexedSeq

/**
  * The number of channels in the final recording.
  */
sealed abstract class RecordingChannels(val value: String) extends EnumEntry {
  override val entryName: String = value
}

object RecordingChannels extends Enum[RecordingChannels] {
  val values: IndexedSeq[RecordingChannels] = findValues

  /**
    * `Mono` records both legs of the call in a single channel of the recording file.
    */
  case object Mono extends RecordingChannels("mono")

  /**
    * `Dual` records each leg to a separate channel of the recording file.
    * The first channel of a dual-channel recording contains the parent call and
    * the second channel contains the child call.
    */
  case object Dual extends RecordingChannels("dual")

  implicit val recordingChannelsDecoder: Decoder[RecordingChannels] =
    enumeratum.Circe.decoder(RecordingChannels)

  implicit val recordingChannelsEncoder: Encoder[RecordingChannels] =
    enumeratum.Circe.encoder(RecordingChannels)
}
