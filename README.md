#twilio4s, an asychronous non-blocking Scala Twilio Client.

twilio4s is a wrapper over the [Twilio](https://www.twilio.com) [REST API](https://www.twilio.com/docs/usage/api). 

twilio4s lets you create requests using case classes and binds JSON response to the Twilio object models.

## Libraries used:

- [akka-http](http://doc.akka.io/docs/akka-http/current/scala.html) for making HTTP requests
- [scala-xml](https://github.com/scala/scala-xml) for parsing XML responses where applicable
- [akka-stream-json](https://github.com/knutwalker/akka-stream-json) for streaming JSON
- [circe](https://circe.github.io/circe/) for JSON parsing, validation and compile-time macros
- [enumeratum](https://github.com/lloydmeta/enumeratum) for providing typesafe enumerations on twilio enum models
- [scala-logging](https://github.com/lightbend/scala-logging) for convenient logging with `logback-classic` as backend
- [scalatest](http://www.scalatest.org/) for unit and integration tests

Currently, twilio4s is in initial development stage.
I am focusing on [programmable voice](https://www.twilio.com/voice) but pull requests to other modules are always welcomed!
 
## Prerequisites
- JDK 8
- Scala 2.11.+ or 2.12.+
- Signup for [free](https://www.twilio.com/console) and obtain your `ACCOUNT SID`, `AUTH TOKEN` and a `trial phone number`

## Usage

Add your `ACCOUNT SID` and `AUTH TOKEN` as:
 
 1. Either environment variables:

```
export TWILIO_ACCOUNT_SID='my-account-sid'
export TWILIO_AUTH_TOKEN='my-auth-token'
```

(or) 

2.
```

    auth {
      account.sid="my-account-sid"
      token="my-auth-token"
    }

```

```
twilio {
  rest {
    auth {
      account.sid="my-account-sid"
      token="my-auth-token"
    }
  }
}
```

## Examples
 
There is currently an integration test that show how the library is intended to be used.

- [Create a call resource]


### Formatting/Style Guide
The project uses `scalafmt` to enforce consistent Scala formatting. 
Please run `scalafmt` inside of sbt before committing your code to Github.

### Testing

Tests can be run with:

```
sbt test
sbt it:test
```
