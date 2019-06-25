package org.aedama.twilio4s.v1

import akka.http.scaladsl.model.headers.{Authorization, BasicHttpCredentials}
import org.aedama.twilio4s.util.Configuration

final case class ApiKey(accountSID: String, authToken: String) {

  private[twilio4s] val toBasicAuthHeader: Authorization =
    Authorization(BasicHttpCredentials(accountSID, authToken))

}

object ApiKey {

  private[twilio4s] def fromConfig: ApiKey =
    ApiKey(Configuration.accountSID, Configuration.authToken)

}
