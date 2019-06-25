package org.aedama.twilio4s.v1.voice.request

import scala.collection.mutable

/**
  * @param statusCallbackOptions   Status callback options
  * @param sendDigits              A string of keys to dial after connecting to the number, maximum of 32 digits
  * @param machineDetectionOptions Machine detection options
  * @param sipOptions              Sip options
  * @param recordingOptions        Recording options
  * @param callerId                The phone number, SIP address, or Client identifier that made this call.
  * @param timeout                 Number of seconds to wait for an answer
  */
case class CreateExtraOptions(statusCallbackOptions: Option[StatusCallbackOptions] = None,
                              sendDigits: Option[String] = None,
                              machineDetectionOptions: Option[MachineDetectionOptions] = None,
                              sipOptions: Option[Sip] = None,
                              recordingOptions: Option[RecordingStatusCallbackOptions] = None,
                              callerId: Option[String] = None,
                              timeout: Option[Int] = None) {

  def toPostParams: Map[String, String] = {
    val params = mutable.Map[String, String]()

    statusCallbackOptions.foreach { options =>
      params += "StatusCallback" -> options.statusCallback.toString
      options.statusCallbackEvent.foreach(o => params += "StatusCallbackEvent" -> o.entryName)
      params += "StatusCallbackMethod" -> options.statusCallbackMethod.entryName
    }

    sendDigits.foreach(params += "SendDigits" -> _)

    machineDetectionOptions.foreach { options =>
      params += "MachineDetection"                   -> options.machineDetection.entryName
      params += "MachineDetectionTimeout"            -> options.machineDetectionTimeout.toString
      params += "MachineDetectionSpeechThreshold"    -> options.machineDetectionSpeechThreshold.toString
      params += "MachineDetectionSpeechEndThreshold" -> options.machineDetectionSpeechEndThreshold.toString
      params += "MachineDetectionSilenceTimeout"     -> options.machineDetectionSilenceTimeout.toString
    }

    sipOptions.foreach { options =>
      params += "SipAuthUsername" -> options.sipAuthUsername
      params += "SipAuthPassword" -> options.sipAuthPassword
    }

    recordingOptions.foreach { options =>
      params += "Record"                        -> options.record.toString
      params += "RecordingChannels"             -> options.recordingChannels.entryName
      params += "RecordingStatusCallback"       -> options.recordingStatusCallback.toString
      params += "RecordingStatusCallbackMethod" -> options.recordingStatusCallbackMethod.entryName
      options.recordingStatusCallbackEvent.foreach(o => params += "RecordingStatusCallbackEvent" -> o.entryName)
      params += "Trim" -> options.trim.entryName
    }

    callerId.foreach(params += "CallerId" -> _)
    timeout.foreach(params += "Timeout"   -> _.toString)

    params.toMap
  }

}
