package database.init

import java.time.LocalDate

import database.DatabaseSchema
import models.{Company, Person}
import play.Logger
import slick.jdbc.H2Profile.api._
import slick.jdbc.meta.MTable

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Success

// TODO: specify execution context
trait InitialDataSeed {
  // Everything that inherits from [[InitialData]] must also inherit from [[DatabaseSchema]],
  // thanks to that we we force child class to have [[DatabaseSchema]]
  // BUT [[InitialData]] IS NOT A [[DatabaseSchema]],
  self: DatabaseSchema =>

  def db: Database

  def createSchemaIfNotExists(): Future[Unit] = {
    Logger.info("Checking schema")
    db.run(MTable.getTables)
      .flatMap((tables: Vector[MTable]) => {
        if (tables.isEmpty)
          db.run(allSchemas.create)
            .andThen { case Success(_) => Logger.info("Schema created")
            case _                     => Logger.info("Could not create schema")
            }
        else {
          Logger.info("Schema already exists")
          Future.successful()
        }
      })
  }

  def insertInitialData(): Future[Unit] = {
    val hogwarts = Company(name = "Hogwarts")
    val google = Company(name = "Google")

    val initCompanies = companies ++= Seq(hogwarts, google)
    val initPeople = {
      people ++= Seq(
        Person(firstName = "Harry", lastName = "Potter", birthDate = LocalDate.of(1990, 1, 1), companyId = hogwarts.id),
        Person(firstName = "Ron", lastName = "Wesley", birthDate = LocalDate.of(1990, 5, 5), companyId = hogwarts.id),
        Person(firstName = "John", lastName = "Smith", birthDate = LocalDate.of(1965, 12, 31), companyId = google.id),
        Person(firstName = "Marry", lastName = "Wilson", birthDate = LocalDate.of(1987, 7, 7), companyId = google.id))
    }

    // Do all the transformations on query and only then run them all at once
    // Sequence of DBIO actions, multiple actions combined to one
    val queries = DBIO.seq(
      companies.delete, people.delete,
      initCompanies,
      initPeople
    )

    db.run(queries)
  }
}
