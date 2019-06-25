package org.aedama.twilio4s.v1

import akka.actor.ActorSystem
import akka.http.scaladsl.{Http, HttpExt}
import akka.stream.ActorMaterializer
import org.aedama.twilio4s.util.Configuration
import org.scalatest.{AsyncWordSpec, Matchers}

import scala.concurrent.ExecutionContext

trait Twilio4SIntegrationTest extends AsyncWordSpec with Matchers {

  implicit val system: ActorSystem = ActorSystem("twilio4s-rest-it")
  override implicit val executionContext: ExecutionContext = system.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val httpClient: HttpExt = Http()
  implicit val authKeys: ApiKey = ApiKey(Configuration.accountSID, Configuration.authToken)

}
