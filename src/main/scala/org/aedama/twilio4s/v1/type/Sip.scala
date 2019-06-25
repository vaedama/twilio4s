package org.aedama.twilio4s.v1.`type`

case class Sip(sip: String) extends Endpoint {

  /**
    * @return endpoint to call
    */
  override def getEndpoint: String = sip
}
