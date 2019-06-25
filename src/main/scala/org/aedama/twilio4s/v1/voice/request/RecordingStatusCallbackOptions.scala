package org.aedama.twilio4s.v1.voice.request

import java.net.URI

import org.aedama.twilio4s.http.HttpMethod

/**
  * @see https://www.twilio.com/docs/voice/api/call#recordingstatuscallback
  * @param recordingStatusCallback       The URL that we call when the recording is available to be accessed
  * @param record                        Whether or not to record the call
  * @param trim                          Whether to trim any leading and trailing silence from the recording
  * @param recordingChannels             The number of channels in the final recording
  * @param recordingStatusCallbackEvent  The recording status event that will trigger calls to the URL specified in `recording_status_callback`
  * @param recordingStatusCallbackMethod The HTTP method we should use when calling the `recording_status_callback` URL
  */
case class RecordingStatusCallbackOptions(
    recordingStatusCallback: URI,
    record: Boolean = false,
    trim: Trim = Trim.TrimSilence,
    recordingChannels: RecordingChannels = RecordingChannels.Mono,
    recordingStatusCallbackEvent: Option[RecordingStatusCallbackEvent] = None, // TODO: make List
    recordingStatusCallbackMethod: HttpMethod = HttpMethod.POST)
