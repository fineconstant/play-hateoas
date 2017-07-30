package database.init

import dao.{CompanyDAO, PeopleDAO}
import database.DatabaseSchema
import play.Logger
import slick.jdbc.H2Profile.api._
import utils.Initializable

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

// TODO: inject database
// TODO: specify execution context
final class InitializeDatabase extends Initializable with DatabaseSchema with InitialDataSeed {

  override val db = Database.forConfig("slick.dbs.h2mem.db")

  val future: Future[Unit] = createSchemaIfNotExists()
    .andThen {
      case _ => insertInitialData()
    }

  // block
  Await.ready(future, Duration.Inf)

  private val companyDAO = new CompanyDAO(db)
  private val peopleDAO = new PeopleDAO(db)

  //companyDAO.indexCompanies
  //  .foreach(c => Logger.info(s"Found company: $c"))

  peopleDAO.indexPeople
    .foreach(c => Logger.info(s"Found person: $c"))

  //companyDAO.companiesWithPeople
  //  .foreach(c => Logger.info(s"Found: $c"))

}
