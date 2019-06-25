package org.aedama.twilio4s.util

import com.typesafe.config.{Config, ConfigException, ConfigFactory}

import scala.util.Properties

object Configuration {

  val config: Config = ConfigFactory.load

  // The SID of the Account that will create the resource
  val accountSID: String = envOrConfig("TWILIO_ACCOUNT_SID", "twilio.rest.auth.account.sid")
  val authToken: String  = envOrConfig("TWILIO_AUTH_TOKEN", "twilio.rest.auth.token")

  val baseUrl: String    = config.getString("twilio.rest.api.url")
  val accountUrl: String = s"$baseUrl/$accountSID"

  def envOrConfig(envProp: String, configProp: String): String =
    try {
      Properties.envOrNone(envProp).getOrElse(config.getString(configProp))
    } catch {
      case _: ConfigException =>
        throw new RuntimeException(
          s"[twilio4s] configuration missing: Environment variable $envProp or configuration $configProp not found.")
    }

}
