package org.aedama.twilio4s.v1.voice.request

import enumeratum.{Enum, EnumEntry}
import io.circe.{Decoder, Encoder}

import scala.collection.immutable.IndexedSeq

/**
  * Detect if a human, answering machine, or fax has picked up the call.
  */
sealed abstract class MachineDetection(val value: String) extends EnumEntry {
  override val entryName: String = value
}

object MachineDetection extends Enum[MachineDetection] {
  val values: IndexedSeq[MachineDetection] = findValues

  /**
    * Use `Enable` if you would like us to return `AnsweredBy` as soon as the called party is identified.
    */
  case object Enable extends MachineDetection("Enable")

  /**
    * Use `DetectMessageEnd`, if you would like to leave a message on an answering machine.
    */
  case object DetectMessageEnd extends MachineDetection("DetectMessageEnd")

  implicit val machineDetectionDecoder: Decoder[MachineDetection] = enumeratum.Circe.decoder(MachineDetection)
  implicit val machineDetectionEncoder: Encoder[MachineDetection] = enumeratum.Circe.encoder(MachineDetection)
}
