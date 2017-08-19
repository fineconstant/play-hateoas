## Play HATEOAS
### HATEOAS REST Api created with Play Framework and Scala

[![Build Status](https://travis-ci.org/kamilduda/play-hateoas.svg?branch=master)](https://travis-ci.org/kamilduda/play-hateoas)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/d19560b83df0463bbd5d649f9264c3c1)](https://www.codacy.com/app/kamilduda/play-hateoas?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=kamilduda/play-hateoas&amp;utm_campaign=Badge_Grade)

#### What's inside?
* SBT auto-reload development `sbt ~ run`
* HTTPS `-Dhttp.port=disabled -Dhttps.port=9443`
* H2 Browser `sbt h2-browser`
* Display project's dependency updates`sbt dependencyUpdates`
* SQL dbs support with [Slick](http://slick.lightbend.com/docs/)
  * change implementation using `database.config.DatabaseProvider`
    * H2 in-memory
    * PostgradeSQL Docker`docker/start-postgres.sh`
* NoSQL dbs support with TODO
