package database.config

import javax.inject.{Inject, Singleton}

import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.PostgresProfile

@Singleton
final class ApplicationDatabaseConfigProvider @Inject()(dbConfigProvider: DatabaseConfigProvider) {
  /** We want the JdbcProfile for this provider */
   val dbConfig = dbConfigProvider.get[PostgresProfile]
}
