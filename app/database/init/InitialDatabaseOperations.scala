package database.init

import javax.inject.{Inject, Singleton}

import models.Company
import repository.{CompaniesRepository, EmployeesRepository}
import utils.io.JsonFileReader

import scala.concurrent.Future

@Singleton
final class InitialDatabaseOperations @Inject()(val companies: CompaniesRepository, val people: EmployeesRepository) {

  private val CompaniesJsonSamplePath = "resources/sample-data/companies-sample.json"
  private val EmployeesJsonSamplePath = "resources/sample-data/employees-sample.json"


  def renameMe(): Unit = {
    JsonFileReader.read[Company](CompaniesJsonSamplePath)
      .foreach(println)

  }


  // TODO: use repository methods
  def createSchemaIfNotExists(): Future[Unit] = {
    //Logger.info("Checking db schema")
    //
    //val allTablesNames = allTables map (_.baseTableRow.tableName)
    //
    //api.db.run(MTable.getTables)
    //  .flatMap(tables => {
    //    val existingTables = tables.map(_.name.name)
    //      .filter(allTablesNames.contains)
    //
    //    if (existingTables.isEmpty)
    //      api.db.run(allSchemas.create)
    //        .andThen {
    //          case Success(_) => Logger.info("Schema created")
    //          case Failure(f) => Logger.info("Could not create schema", f)
    //        }
    //    else {
    //      Logger.info("Schema already exists")
    //      existingTables.foreach(table => Logger.info(s"Found table: $table"))
    //    }
    //
    //    Future.successful()
    //  })
    ???
  }

  // TODO: use repository methods
  def insertInitialData(): Future[Unit] = {
    //Logger.info("Clearing tables and inserting sample data")
    //val amazon = Company(name = "Amazon")
    //val google = Company(name = "Google")
    //val microsoft = Company(name = "Microsoft")
    //val companiesSeed = Seq(amazon, google, microsoft)
    //
    //val peopleSeed = {
    //  Seq(Employee(firstName = "Harry", lastName = "Potter", birthDate = LocalDate.of(1989, 1, 1), companyId = amazon
    //    .id),
    //      Employee(firstName = "Ron", lastName = "Wesley", birthDate = LocalDate.of(1990, 5, 5), companyId = amazon.id),
    //      Employee(firstName = "John", lastName = "Smith", birthDate = LocalDate.of(1965, 12, 31), companyId = google
    //        .id),
    //      Employee(firstName = "Marry", lastName = "Wilson", birthDate = LocalDate.of(1987, 7, 7), companyId = google
    //        .id))
    //}
    //
    //// Do all the transformations on query and only then run them all at once
    //// Sequence of DBIO actions, multiple actions combined as one
    //val queries = DBIO.seq(
    //  people.delete, companies.delete,
    //  companies ++= companiesSeed,
    //  people ++= peopleSeed
    //)
    //
    //db.run(queries)
    ???
  }

  def dropAllSchemas(): Future[Unit] = {
    //db.run(allSchemas.drop)
    ???
  }

  def truncateAllSchemas(): Future[Unit] = {
    //db.run(allSchemas.truncate)
    ???
  }
}
