package org.aedama.twilio4s.http

import enumeratum.{Enum, EnumEntry}
import io.circe.{Decoder, Encoder}

import scala.collection.immutable

sealed abstract class HttpMethod(val value: String) extends EnumEntry {
  override val entryName: String = value
}

object HttpMethod extends Enum[HttpMethod] {
  val values: immutable.IndexedSeq[HttpMethod] = findValues

  case object GET extends HttpMethod("GET")

  case object POST extends HttpMethod("POST")

  case object PUT extends HttpMethod("PUT")

  case object DELETE extends HttpMethod("DELETE")

  case object HEAD extends HttpMethod("HEAD")

  case object OPTIONS extends HttpMethod("OPTIONS")

  implicit val httpMethodDecoder: Decoder[HttpMethod] = enumeratum.Circe.decoder(HttpMethod)
  implicit val httpMethodEncoder: Encoder[HttpMethod] = enumeratum.Circe.encoder(HttpMethod)
}
