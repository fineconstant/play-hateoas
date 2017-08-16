package database.init

import java.time.LocalDate

import database.schema.DatabaseSchema
import models.{Company, Person}
import play.Logger
import slick.jdbc.PostgresProfile.api._
import slick.jdbc.meta.MTable

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

// TODO: specify execution context
trait InitialDatabaseSeed {
  // Everything that inherits from [[InitialData]] must also inherit from [[DatabaseSchema]],
  // thanks to that we force child class to have [[DatabaseSchema]]
  // [[InitialData]] IS NOT A [[DatabaseSchema]],
  self: DatabaseSchema =>

  def db: Database

  def createSchemaIfNotExists(): Future[Unit] = {
    Logger.info("Checking db schema")

    val allTablesNames = allTables map (_.baseTableRow.tableName)

    db.run(MTable.getTables)
      .flatMap(tables => {
        val existingTables = tables.map(_.name.name)
          .filter(allTablesNames.contains)

        if (existingTables.isEmpty)
          db.run(allSchemas.create)
            .andThen {
              case Success(_) => Logger.info("Schema created")
              case Failure(f) => Logger.info("Could not create schema", f)
            }
        else {
          Logger.info("Schema already exists")
          existingTables.foreach(table => Logger.info(s"Found table: $table"))
        }

        Future.successful()
      })
  }

  // TODO: read initial data from some file
  def insertInitialData(): Future[Unit] = {
    Logger.info("Clearing tables and inserting sample data")
    val amazon = Company(name = "Amazon")
    val google = Company(name = "Google")
    val microsoft = Company(name = "Microsoft")
    val companiesSeed = Seq(amazon, google, microsoft)

    val peopleSeed = {
      Seq(Person(firstName = "Harry", lastName = "Potter", birthDate = LocalDate.of(1989, 1, 1), companyId = amazon.id),
          Person(firstName = "Ron", lastName = "Wesley", birthDate = LocalDate.of(1990, 5, 5), companyId = amazon.id),
          Person(firstName = "John", lastName = "Smith", birthDate = LocalDate.of(1965, 12, 31), companyId = google.id),
          Person(firstName = "Marry", lastName = "Wilson", birthDate = LocalDate.of(1987, 7, 7), companyId = google.id))
    }

    // Do all the transformations on query and only then run them all at once
    // Sequence of DBIO actions, multiple actions combined as one
    val queries = DBIO.seq(
      people.delete, companies.delete,
      companies ++= companiesSeed,
      people ++= peopleSeed
    )

    db.run(queries)
  }

  def dropAllSchemas(): Future[Unit] = {
    db.run(allSchemas.drop)
  }

  def truncateAllSchemas(): Future[Unit] = {
    db.run(allSchemas.truncate)
  }
}
