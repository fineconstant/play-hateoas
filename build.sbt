name := """play-hateoas"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

resolvers += Resolver.sonatypeRepo("snapshots")

scalaVersion := "2.12.2"

val versions = Map("h2" -> "1.4.196",
                   "scalatestplus-play" -> "3.1.1")

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % versions("scalatestplus-play") % Test
libraryDependencies += "com.h2database" % "h2" % versions("h2")
