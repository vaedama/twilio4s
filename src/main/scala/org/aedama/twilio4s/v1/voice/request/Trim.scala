package org.aedama.twilio4s.v1.voice.request

import enumeratum.{Enum, EnumEntry}
import io.circe.{Decoder, Encoder}

import scala.collection.immutable.IndexedSeq

/**
  * Options to trim any leading and trailing silence from the recording.
  */
sealed abstract class Trim(val value: String) extends EnumEntry {
  override val entryName: String = value
}

object Trim extends Enum[Trim] {
  val values: IndexedSeq[Trim] = findValues

  case object TrimSilence extends Trim("trim-silence")

  case object DoNotTrim extends Trim("do-not-trim")

  implicit val trimDecoder: Decoder[Trim] = enumeratum.Circe.decoder(Trim)
  implicit val trimEncoder: Encoder[Trim] = enumeratum.Circe.encoder(Trim)
}
