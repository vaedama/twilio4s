package org.aedama.twilio4s.v1.voice.request

import java.net.URI

import org.aedama.twilio4s.http.HttpMethod

/**
  * @param fallbackUrl    Fallback URL in case of error
  * @param fallbackMethod HTTP Method to use with fallback_url
  */
case class CreateFallbackOptions(fallbackUrl: URI, fallbackMethod: HttpMethod = HttpMethod.POST) {

  def toPostParams: Map[String, String] =
    Map("FallbackUrl" -> fallbackUrl.toString, "FallbackMethod" -> fallbackMethod.entryName)

}
