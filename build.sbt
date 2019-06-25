name := "twilio4s"

val currentScalaVersion = "2.12.8"
val scala211Version = "2.11.11"

scalaVersion := currentScalaVersion

crossScalaVersions := Seq(currentScalaVersion, scala211Version)

organization := "org.aedama"

scalacOptions ++= Seq(
  "-Xfatal-warnings",
  "-target:jvm-1.8",
  "-encoding",
  "UTF-8",
  "-deprecation", // warning and location for usages of deprecated APIs
  "-feature", // warning and location for usages of features that should be imported explicitly
  "-unchecked", // additional warnings where generated code depends on assumptions
  "-Xlint", // recommended additional warnings
  "-Xcheckinit", // runtime error when a val is not initialized due to trait hierarchies (instead of NPE somewhere else)
  "-Ywarn-adapted-args", // Warn if an argument list is modified to match the receiver
  "-Ywarn-value-discard", // Warn when non-Unit expression results are unused
  "-Ywarn-inaccessible",
  "-Ywarn-dead-code",
  "-language:postfixOps"
)

Defaults.itSettings
configs(IntegrationTest)
parallelExecution in IntegrationTest := false

val akkaHttpVersion = "10.1.8"
val akkaVersion = "2.6.0-M3"
val akkaStreamJson = "3.4.0"
val circeVersion = "0.9.3" //"0.11.1"
val enumeratumVersion = "1.5.12" //"1.5.13"
val enumeratumCirceVersion = "1.5.14" // "1.5.21"
val logbackClassic = "1.2.3"
val scalaLogging = "3.9.2"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "de.knutwalker" %% "akka-http-circe" % akkaStreamJson,
  "de.knutwalker" %% "akka-stream-circe" % akkaStreamJson,
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "com.beachape" %% "enumeratum" % enumeratumVersion,
  "com.beachape" %% "enumeratum-circe" % enumeratumCirceVersion,
  "com.typesafe.scala-logging" %% "scala-logging" % scalaLogging,
  "ch.qos.logback" % "logback-classic" % logbackClassic,
  "org.scala-lang.modules" %% "scala-xml" % "1.2.0",
  "org.scalatest" %% "scalatest" % "3.0.3" % "test, it"
)

publishMavenStyle := true

val flagsFor11 = Seq(
  "-Yconst-opt",
  "-Ywarn-infer-any",
  "-Yclosure-elim",
  "-Ydead-code"
)

val flagsFor12 = Seq(
  "-Xlint:-unused", //because 2.11 needs unused import: cats.syntax.either._
  "-Ywarn-infer-any",
  "-opt-inline-from:<sources>"
)

scalacOptions ++= {
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, n)) if n >= 12 =>
      flagsFor12
    case Some((2, n)) if n == 11 =>
      flagsFor11
  }
}




//lazy val root = (project in file(".")).
//  settings(
//    inThisBuild(List(
//     ,
//      scalaVersion := "2.12.8"
//    )),
//    name := "twilio4s",
//    scalacOptions ++= Seq(
//      "-Xfatal-warnings",
//      "-target:jvm-1.8",
//      "-encoding",
//      "UTF-8",
//      "-deprecation",         // warning and location for usages of deprecated APIs
//      "-feature",             // warning and location for usages of features that should be imported explicitly
//      "-unchecked",           // additional warnings where generated code depends on assumptions
//      "-Xlint",               // recommended additional warnings
//      "-Xcheckinit",          // runtime error when a val is not initialized due to trait hierarchies (instead of NPE somewhere else)
//      "-Ywarn-adapted-args",  // Warn if an argument list is modified to match the receiver
//      "-Ywarn-value-discard", // Warn when non-Unit expression results are unused
//      "-Ywarn-inaccessible",
//      "-Ywarn-dead-code",
//      "-language:postfixOps"
//    ),
//    libraryDependencies ++= {
//
//
//
//
//
//
//      val AkkaVersion = "2.6.0-M3"
//
//      
//      
//      val ScalaTest = "3.0.5"
//
//
//
//      Seq(
//        "com.typesafe.akka"          %% "akka-http"         % akkaHttpVersion,
//        "com.typesafe.akka"          %% "akka-http-xml"     % akkaHttpVersion,
//
//        "de.knutwalker"              %% "akka-stream-circe" % akkaStreamJson,
//        "de.knutwalker"              %% "akka-http-circe"   % akkaStreamJson,
//
//        "io.circe"                   %% "circe-core"        % circeVersion,
//        "io.circe"                   %% "circe-generic"     % circeVersion,
//        "io.circe"                   %% "circe-parser"      % circeVersion,
//
//        "com.beachape"               %% "enumeratum"        % enumeratumVersion,
//        "com.beachape"               %% "enumeratum-circe"  % enumeratumCirceVersion,
//
//        "com.typesafe.scala-logging" %% "scala-logging"     % "3.7.2",
//        
//        "org.scalatest"              %% "scalatest"         % "3.0.3" % "test"
////        "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
////
////
////        "io.circe"                   %% "circe-core"        % circeVersion,
////        "io.circe"                   %% "circe-generic"     % circeVersion,
////        "io.circe"                   %% "circe-parser"      % circeVersion,
////        "com.beachape"               %% "enumeratum"        % enumeratumVersion,
////        "com.beachape"               %% "enumeratum-circe"  % enumeratumCirceVersion,
////
////        "com.typesafe.scala-logging" %% "scala-logging" % ScalaLogging,
////        "org.scalatest" %% "scalatest" % ScalaTest % Test
//      )
//    }
//  )
