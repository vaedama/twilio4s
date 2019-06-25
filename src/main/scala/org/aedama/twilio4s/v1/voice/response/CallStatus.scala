package org.aedama.twilio4s.v1.voice.response

import enumeratum.{Enum, EnumEntry}
import io.circe.{Decoder, Encoder}

import scala.collection.immutable.IndexedSeq

sealed abstract class CallStatus(val value: String) extends EnumEntry {
  override val entryName: String = value
}

object CallStatus extends Enum[CallStatus] {
  val values: IndexedSeq[CallStatus] = findValues

  case object Queued extends CallStatus("queued")

  case object Ringing extends CallStatus("ringing")

  case object InProgress extends CallStatus("in-progress")

  case object Canceled extends CallStatus("canceled")

  case object Completed extends CallStatus("completed")

  case object Busy extends CallStatus("busy")

  case object Failed extends CallStatus("failed")

  implicit val callStatusDecoder: Decoder[CallStatus] = enumeratum.Circe.decoder(CallStatus)
  implicit val callStatusEncoder: Encoder[CallStatus] = enumeratum.Circe.encoder(CallStatus)
}
