addSbtPlugin("io.get-coursier" % "sbt-coursier" % "1.0.0-RC13")

// The Play Plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.6.7")

// Play Auto Refresh: https://github.com/jamesward/play-auto-refresh
addSbtPlugin("com.jamesward" % "play-auto-refresh" % "0.0.16")

//sbt-buildinfo: https://github.com/sbt/sbt-buildinfo
addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.7.0")

/** Code Coverage */
// Scala Scoverage SBT plugin: https://github.com/scoverage/sbt-scoverage#sbt-scoverage
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.1")
// Codacy static analysis code coverage: https://github.com/codacy/sbt-codacy-coverage#sbt-codacy-coverage
addSbtPlugin("com.codacy" % "sbt-codacy-coverage" % "1.3.11")
