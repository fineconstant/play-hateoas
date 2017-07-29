import com.jamesward.play.BrowserNotifierKeys

name := """play-hateoas"""
version := "1.0-SNAPSHOT"
homepage := Some(url("https://github.com/kamilduda/play-hateoas"))
organization := "org.kduda"
organizationHomepage := Some(url("https://github.com/kamilduda"))

lazy val root = (project in file(".")).enablePlugins(PlayScala)

resolvers += Resolver.sonatypeRepo("snapshots")

scalaVersion := "2.12.2"

val h2Version = "1.4.196"
val scalaTestPlusPlayVersion = "3.1.1"

// Dev
libraryDependencies += guice
libraryDependencies += "com.h2database" % "h2" % h2Version

// Test
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % scalaTestPlusPlayVersion % Test

// Do not open browser window on sbt run (for IntelliJ PlayFramework run configuration)
BrowserNotifierKeys.shouldOpenBrowser := false
