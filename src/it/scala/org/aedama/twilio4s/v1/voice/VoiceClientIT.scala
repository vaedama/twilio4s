package org.aedama.twilio4s.v1.voice

import java.net.URI

import org.aedama.twilio4s.util.Configuration
import org.aedama.twilio4s.v1.Twilio4SIntegrationTest
import org.aedama.twilio4s.v1.`type`.PhoneNumber
import org.aedama.twilio4s.v1.voice.response.CreateCallResponse

class VoiceClientIT extends Twilio4SIntegrationTest with VoiceClient {

  "Call" should {
    "create resource" in {
      Call.create(
        PhoneNumber(VoiceClientIT.toPhone),
        PhoneNumber(VoiceClientIT.fromPhone),
        new URI(VoiceClientIT.url)).map { eitherErrorOrResponse =>
        eitherErrorOrResponse shouldBe a[Right[_, CreateCallResponse]]
        eitherErrorOrResponse.right.get.get.account_sid should be(Configuration.accountSID)
      }
    }
  }

}

object VoiceClientIT {

  val fromPhone: String = Configuration.config.getString("twilio.rest.voice.call.from")
  val toPhone: String = Configuration.config.getString("twilio.rest.voice.call.to")
  val url = "http://demo.twilio.com/docs/voice.xml"

}