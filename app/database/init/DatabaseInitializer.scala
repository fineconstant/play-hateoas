package database.init

import javax.inject.Inject

import lifecycle.Initializable
import play.Logger
import play.api.mvc.ControllerComponents
import repository.{CompaniesRepository, EmployeesRepository}
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

final class DatabaseInitializer @Inject()(ops: InitialDatabaseOperations)
  extends Initializable {

  // TODO: run in sequence
  //Await.result(seed.dropAllSchemas(), Duration.Inf)
  //Await.result(seed.createSchemaIfNotExists(), Duration.Inf)
  //Await.result(seed.insertInitialData(), Duration.Inf)

  ops.renameMe()

  //seed.companies.stream
  //  .foreach(c => Logger.info(s"Found company: $c"))
  //
  //seed.people.stream
  //  .foreach(c => Logger.info(s"Found person: $c"))
  //
  //seed.companies.withEmployees
  //  .foreach(c => Logger.info(s"Found company with people: $c"))

}
