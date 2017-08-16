package database.init

import akka.actor.Status.Success
import dao.{CompanyDAO, PeopleDAO}
import database.schema.DatabaseSchema
import models.Person
import play.Logger
import slick.jdbc.PostgresProfile.api._
import utils.Initializable

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.Try

// TODO: inject database
// TODO: inject DAO
// TODO: specify execution context instead of import scala.concurrent.ExecutionContext.Implicits.global
final class InitializeDatabase extends Initializable with DatabaseSchema with InitialDatabaseSeed {

  override val db = Database.forConfig("slick.postgres")


  // TODO: run in sequence
  //Await.result(dropAllSchemas(), Duration.Inf)
  Await.result(createSchemaIfNotExists(), Duration.Inf)
  Await.result(insertInitialData(), Duration.Inf)

  private val companyDAO = new CompanyDAO(db)
  private val peopleDAO = new PeopleDAO(db)

  companyDAO.indexCompanies
    .foreach(c => Logger.info(s"Found company: $c"))

  peopleDAO.indexPeople
    .foreach(c => Logger.info(s"Found person: $c"))

  companyDAO.companiesWithPeople
    .foreach(c => Logger.info(s"Found company with people: $c"))

}
