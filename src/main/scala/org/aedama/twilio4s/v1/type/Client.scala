package org.aedama.twilio4s.v1.`type`

object Client {
  val PREFIX = "client:"
}

case class Client(client: String) extends Endpoint {

  override def getEndpoint: String =
    if (!client.toLowerCase.startsWith(Client.PREFIX)) Client.PREFIX + client else client

}
