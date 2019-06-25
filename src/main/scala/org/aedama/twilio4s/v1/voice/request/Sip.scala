package org.aedama.twilio4s.v1.voice.request

/**
  * @param sipAuthUsername The username used to authenticate the caller making a SIP call
  * @param sipAuthPassword The password required to authenticate the user account specified in `sip_auth_username`.
  */
case class Sip(sipAuthUsername: String, sipAuthPassword: String)
