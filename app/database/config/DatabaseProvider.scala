package database.config

import javax.inject.{Inject, Singleton}

import play.api.db.slick.DatabaseConfigProvider
import play.db.NamedDatabase
import slick.jdbc.{H2Profile, PostgresProfile}

/**
  * Change db by selecting a desired NamedDatabase and getting a proper slick.jdbc.JdbcProfile
  * eg. h2" with [[H2Profile]] or "postgres" with [[PostgresProfile]]
  */
@Singleton
final class DatabaseProvider @Inject()(@NamedDatabase("postgres") configProvider: DatabaseConfigProvider) {
  val dbConfig = configProvider.get[PostgresProfile]
}
