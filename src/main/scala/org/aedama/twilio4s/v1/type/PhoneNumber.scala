package org.aedama.twilio4s.v1.`type`

case class PhoneNumber(number: String) extends Endpoint {

  /**
    * @return endpoint to call
    */
  override def getEndpoint: String = number
}
