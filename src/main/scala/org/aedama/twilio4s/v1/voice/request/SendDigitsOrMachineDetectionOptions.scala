package org.aedama.twilio4s.v1.voice.request

/**
  * @param sendDigits              The digits to dial after connecting to the number
  * @param machineDetectionOptions Options for MachineDetection
  */
case class SendDigitsOrMachineDetectionOptions(sendDigits: String,
                                               machineDetectionOptions: Option[MachineDetectionOptions] = None)
