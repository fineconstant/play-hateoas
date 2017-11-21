## Play HATEOAS
### HATEOAS REST Api created with Play Framework and Scala

[![Build Status](https://travis-ci.org/kamilduda/play-hateoas.svg?branch=master)](https://travis-ci.org/kamilduda/play-hateoas)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/d19560b83df0463bbd5d649f9264c3c1)](https://www.codacy.com/app/kamilduda/play-hateoas?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=kamilduda/play-hateoas&amp;utm_campaign=Badge_Grade)
[![Codacy Badge](https://api.codacy.com/project/badge/Coverage/d19560b83df0463bbd5d649f9264c3c1)](https://www.codacy.com/app/kamilduda/play-hateoas?utm_source=github.com&utm_medium=referral&utm_content=kamilduda/play-hateoas&utm_campaign=Badge_Coverage)

#### What's inside?
* SBT auto-reload development `sbt ~ run`
* HTTPS `-Dhttp.port=disabled -Dhttps.port=9443`
* H2 Browser (if using H2) `sbt h2-browser`
* Display project's dependency updates`sbt dependencyUpdates`
* SQL dbs support with [Slick](http://slick.lightbend.com/docs/)
  * change implementation using `database.config.DatabaseProvider`
    * H2 in-memory
    * PostgradeSQL Docker `docker/start-postgres.sh`
* Database migrations with Flyway-Play
  * Web endpoints: 
    * `/@flyway`
    * `/@flyway/default`
* Database operations using custom `DatabaseExecutionContext`


#### How to build and run on PRD?
* Create a packaged .zip artifact `target/universal/play-hateoas-1.0-SNAPSHOT.zip` that contains the application along with run scripts
  * Build with SBT command `sbt dist`
  * Unzip `unzip target/universal/play-hateoas-1.0-SNAPSHOT.zip`
  * Start on HTTP: `play-hateoas-1.0-SNAPSHOT/bin/play-hateoas -Dplay.http.secret.key=[secret]`
  * Start on HTTPS: `play-hateoas-1.0-SNAPSHOT/bin/play-hateoas -Dplay.http.secret.key=[secret] -Dhttp.port=disabled -Dhttps.port=[port]` 
* If you do not want to create a whole distribution but only run PRD locally
  * Build an application using SBT command `sbt stage`
  * Directory `target/universal/stage` will be created along with `target/universal/stage/bin/play-hateoas` start scripts
  * Start on HTTP: `target/universal/stage/bin/play-hateoas -Dplay.http.secret.key=[secret]`
  * Start on HTTPS: `target/universal/stage/bin/play-hateoas -Dplay.http.secret.key=[secret] -Dhttp.port=disabled -Dhttps.port=[port]`


#### TODO:
* Create SQL Migrations using some tools
* Provide NoSQL databases support (Cassandra, Elasticsearch)
* Update to JDK 9
  * `javax.xml.bin" % jaxb-api` dependency for Akka and Scala was needed previously (JDK 9 compatibility)
  * There were some problems with Couorsier and JDK 9
* Executable jar with `sbt-assembly` [plugin](https://github.com/sbt/sbt-assembly)
