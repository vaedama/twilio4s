package org.aedama.twilio4s.v1.voice.request

import java.net.URI

import org.aedama.twilio4s.http.HttpMethod

/**
  * @see https://www.twilio.com/docs/voice/api/call#statuscallback
  * @param statusCallback       The URL we should call to send status information to your application
  * @param statusCallbackEvent  The call progress event that we send to the `status_callback` URL.
  * @param statusCallbackMethod HTTP Method to use with status_callback
  */
case class StatusCallbackOptions(statusCallback: URI,
                                 statusCallbackEvent: Option[StatusCallbackEvent] = None, // TODO: make List
                                 statusCallbackMethod: HttpMethod = HttpMethod.POST)
