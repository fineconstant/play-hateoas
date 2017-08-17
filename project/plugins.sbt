resolvers += "Typesafe Repository" at "https://repo.typesafe.com/typesafe/releases/"

// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.6.3")

// Coursier
addSbtPlugin("io.get-coursier" % "sbt-coursier" % "1.0.0-RC10")

// Play Auto Refresh
addSbtPlugin("com.jamesward" % "play-auto-refresh" % "0.0.15")

// Scala Scoverage SBT plugin
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.0")
// Codacy static analysis code coverage
addSbtPlugin("com.codacy" % "sbt-codacy-coverage" % "1.3.8")
