package repository.api

import slick.jdbc.JdbcProfile

trait DBComponent {
  val profile: JdbcProfile

  import profile.api._

  val db: Database
}
