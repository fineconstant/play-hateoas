/** Metadata **/
name := "play-hateoas"
version := "0.1-SNAPSHOT"
homepage := Some(url("https://github.com/kamilduda/play-hateoas"))
organization := "com.kduda"
organizationHomepage := Some(url("https://github.com/kamilduda"))

resolvers += Resolver.sonatypeRepo("snapshots")

scalaVersion := "2.12.4"

// sbt-buildinfo: https://github.com/sbt/sbt-buildinfo
buildInfoKeys ++= Seq[BuildInfoKey](
  resolvers,
  libraryDependencies in Test,
  BuildInfoKey.map(name) {case (k, v) => "project" + k.capitalize -> v.capitalize},
  BuildInfoKey.action("buildTime") {
    System.currentTimeMillis
  }
)
buildInfoOptions += BuildInfoOption.ToMap
buildInfoOptions += BuildInfoOption.ToJson
buildInfoOptions += BuildInfoOption.BuildTime
// end sbt-buildinfo

lazy val root = (project in file(".")).
  enablePlugins(PlayScala).
  enablePlugins(BuildInfoPlugin).
  settings(
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "com.kduda.playhateoas"
  )


/** Dev **/
libraryDependencies += guice
libraryDependencies += jdbc
libraryDependencies += "org.scalaz" %% "scalaz-core" % scalazVersion
// Slick
libraryDependencies += "com.typesafe.play" %% "play-slick" % playSlickVersion
libraryDependencies += "com.lightbend.akka" %% "akka-stream-alpakka-slick" % akkaStreamAlpakkaSlickVersion
// Akka
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % akkaActorsVersion
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % akkaStreamVersion
// DBs
libraryDependencies += "org.flywaydb" %% "flyway-play" % playFlywayVersion
libraryDependencies += "org.postgresql" % "postgresql" % postgresqlVersion
libraryDependencies += "com.h2database" % "h2" % h2Version

// was required for Akka and Scala to work with JDK 9 (JDK 9 compatibility)
libraryDependencies += "javax.xml.bind" % "jaxb-api" % "2.3.0"

/** Test **/
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % scalaTestPlusPlayVersion % Test
libraryDependencies += "org.scalamock" %% "scalamock-scalatest-support" % scalamockVersion % Test
libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % akkaActorsVersion % Test
libraryDependencies += "com.typesafe.akka" %% "akka-stream-testkit" % akkaStreamVersion % Test

/** Versions **/
val scalazVersion = "7.2.18"
val postgresqlVersion = "42.1.4"
val h2Version = "1.4.196"
val playSlickVersion = "3.0.3"
val akkaStreamAlpakkaSlickVersion = "0.16"
val akkaActorsVersion = "2.5.9"
val akkaStreamVersion = "2.5.9"
val playFlywayVersion = "4.0.0"

val scalaTestPlusPlayVersion = "3.1.2"
val scalamockVersion = "3.6.0"


// Do not open additional browser window on sbt run (for IntelliJ's PlayFramework run configuration)
//BrowserNotifierKeys.shouldOpenBrowser := false

// SBT Assembly merge strategies
assemblyMergeStrategy in assembly := {
  case PathList(xs@_*) if xs.last == "reference-overrides.conf" => MergeStrategy.concat
  case x                                                        =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}
// SBT Assembly prepend shell script to the fat jar:
import sbtassembly.AssemblyPlugin.defaultShellScript
assemblyOption in assembly := (assemblyOption in assembly).value.copy(prependShellScript = Some(defaultShellScript))
assemblyJarName in assembly := s"${name.value}-${version.value}"
