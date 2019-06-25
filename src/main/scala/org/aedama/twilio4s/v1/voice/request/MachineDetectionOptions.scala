package org.aedama.twilio4s.v1.voice.request

/**
  * @param machineDetection                Enable machine detection or end of greeting detection
  * @param machineDetectionTimeout         Number of seconds to wait for machine detection
  * @param machineDetectionSpeechThreshold Number of milliseconds for measuring stick for the length of the speech activity
  */
case class MachineDetectionOptions(machineDetection: MachineDetection,
                                   machineDetectionTimeout: Int = 30,
                                   machineDetectionSpeechThreshold: Int = 2400,
                                   machineDetectionSpeechEndThreshold: Int = 1200,
                                   machineDetectionSilenceTimeout: Int = 5000) {
  require(1000 <= machineDetectionSpeechThreshold && machineDetectionSpeechThreshold <= 6000,
          "machineDetectionSpeechThreshold possible values: [1000-6000]")

  require(500 <= machineDetectionSpeechEndThreshold && machineDetectionSpeechEndThreshold <= 5000,
          "machineDetectionSpeechEndThreshold possible values: [1000-5000]")

  require(2000 <= machineDetectionSilenceTimeout && machineDetectionSilenceTimeout <= 10000,
          "machineDetectionSilenceTimeout possible values: [1000-5000]")
}
