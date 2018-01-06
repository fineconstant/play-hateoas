package repository.api

import slick.jdbc.JdbcProfile

trait JDBCProfileAware {
  val profile: JdbcProfile
}
