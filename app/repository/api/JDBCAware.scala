package repository.api

import akka.stream.alpakka.slick.scaladsl.SlickSession
import slick.jdbc.{JdbcBackend, JdbcProfile}

trait JDBCAware {
  val profile: JdbcProfile
  val session: SlickSession
  val db: JdbcBackend#DatabaseDef
}
