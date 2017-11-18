// Metadata
name := """play-hateoas"""
version := "1.0-SNAPSHOT"
homepage := Some(url("https://github.com/kamilduda/play-hateoas"))
organization := "org.kduda"
organizationHomepage := Some(url("https://github.com/kamilduda"))

resolvers += Resolver.sonatypeRepo("snapshots")

scalaVersion := "2.12.4"

lazy val root = (project in file(".")) enablePlugins(PlayScala)


// Versions
val scalazVersion = "7.2.16"
val postgresqlVersion = "42.1.4"
val h2Version = "1.4.196"
val playSlickVersion = "3.0.2"
val akkaActorsVersion = "2.5.6"
val akkaStreamVersion = "2.5.6"
val jaxbapiVersion = "2.3.0"

val scalaTestPlusPlayVersion = "3.1.2"

// Dev
libraryDependencies += guice
libraryDependencies += jdbc
libraryDependencies += "org.scalaz" %% "scalaz-core" % scalazVersion
libraryDependencies += "org.postgresql" % "postgresql" % postgresqlVersion
libraryDependencies += "com.h2database" % "h2" % h2Version
libraryDependencies += "com.typesafe.play" %% "play-slick" % playSlickVersion
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % akkaActorsVersion
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % akkaStreamVersion

// was required for Akka and Scala to work with JDK 9 (JDK 9 compatibility)
//libraryDependencies += "javax.xml.bind" % "jaxb-api" % jaxbapiVersion

// Test
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % scalaTestPlusPlayVersion % Test
libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % akkaActorsVersion % Test
libraryDependencies += "com.typesafe.akka" %% "akka-stream-testkit" % akkaStreamVersion % Test

// Do not open browser window on sbt run (for IntelliJ's PlayFramework run configuration)
//BrowserNotifierKeys.shouldOpenBrowser := false
