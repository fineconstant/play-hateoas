package database.init

import javax.inject.Inject

import dao.CompanyDAO
import database.DatabaseSchema
import slick.jdbc.H2Profile.api._
import utils.Initializable

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

// TODO: inject database
// TODO: specify execution context
final class InitializeDatabase extends Initializable with DatabaseSchema with InitialDataSeed {

  override val db = Database.forConfig("slick.dbs.h2mem")

  val future: Future[Unit] = createSchemaIfNotExists()
    .flatMap(_ => insertInitialData())

  // block
  Await.ready(future, Duration.Inf)
    .foreach(println)


  println("test")
  private val companyDAO = new CompanyDAO(db)
  val companyFuture = companyDAO.companiesWithPeople
  Await.ready(companyFuture, Duration.Inf)
    .foreach(println)

}
