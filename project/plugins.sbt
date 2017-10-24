resolvers += "Typesafe Repository" at "https://repo.typesafe.com/typesafe/releases/"

// Coursier: https://github.com/coursier/coursier#quick-start
// TODO: migrate Coursier to version working with SBT 1.x
// TODO: delete all coursier and sbt cache and local repositories
//addSbtPlugin("io.get-coursier" % "sbt-coursier" % "1.0.0-RC12")

// The Play Plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.6.6")

// Play Auto Refresh: https://github.com/jamesward/play-auto-refresh#setup
addSbtPlugin("com.jamesward" % "play-auto-refresh" % "0.0.16")

/** Code Coverage */
// Scala Scoverage SBT plugin: https://github.com/scoverage/sbt-scoverage#sbt-scoverage
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.1")
// Codacy static analysis code coverage: https://github.com/codacy/sbt-codacy-coverage#sbt-codacy-coverage
addSbtPlugin("com.codacy" % "sbt-codacy-coverage" % "1.3.11")
