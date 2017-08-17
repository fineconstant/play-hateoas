import com.jamesward.play.BrowserNotifierKeys

// Metadata
name := """play-hateoas"""
version := "1.0-SNAPSHOT"
homepage := Some(url("https://github.com/kamilduda/play-hateoas"))
organization := "org.kduda"
organizationHomepage := Some(url("https://github.com/kamilduda"))

lazy val root = (project in file(".")).enablePlugins(PlayScala)

resolvers += Resolver.sonatypeRepo("snapshots")

scalaVersion := "2.12.3"

// Versions
val scalazVersion = "7.2.15"
val postgresqlVersion = "42.1.4"
val playSlickVersion = "3.0.1"
val slickCodegenVersion = "3.2.1"
val slickForkliftVersion = "0.3.0"

val scalaTestPlusPlayVersion = "3.1.1"

// Dev
libraryDependencies += guice
libraryDependencies += jdbc
libraryDependencies += "org.scalaz" %% "scalaz-core" % scalazVersion
libraryDependencies += "org.postgresql" % "postgresql" % postgresqlVersion
libraryDependencies += "com.typesafe.play" %% "play-slick" % playSlickVersion
//libraryDependencies += "com.typesafe.slick" %% "slick-codegen" % slickCodegenVersion
//libraryDependencies += "com.liyaos" %% "scala-forklift-slick" % slickForkliftVersion

// Test
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % scalaTestPlusPlayVersion % Test

// Do not open browser window on sbt run (for IntelliJ's PlayFramework run configuration)
BrowserNotifierKeys.shouldOpenBrowser := false
