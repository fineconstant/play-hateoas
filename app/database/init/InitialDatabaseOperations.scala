package database.init

import javax.inject.{Inject, Singleton}

import models.{Company, Employee}
import repository.{CompaniesRepository, EmployeesRepository}
import utils.io.JsonFileReader

import scala.concurrent.Await
import scala.concurrent.duration.Duration

@Singleton
final class InitialDatabaseOperations @Inject()(
  val companies: CompaniesRepository, val employees: EmployeesRepository) {

  import InitialDatabaseOperations._

  def dropAllTablesIfExist(): Unit = {
    Await.result(employees.dropTableIfExists(), Duration.Inf)
    Await.result(companies.dropTableIfExists(), Duration.Inf)
  }

  def createSchemaIfNotExists(): Unit = {
    Await.result(companies.createSchemaIfNotExists(), Duration.Inf)
    Await.result(employees.createSchemaIfNotExists(), Duration.Inf)
  }

  def initializeWithSampleData(): Unit = {
    Await.result(companies.save(JsonFileReader.read[Company](CompaniesJsonSamplePath)), Duration.Inf)
    Await.result(employees.save(JsonFileReader.read[Employee](EmployeesJsonSamplePath)), Duration.Inf)
  }
}

object InitialDatabaseOperations {
  val CompaniesJsonSamplePath = "resources/sample-data/companies-sample.json"
  val EmployeesJsonSamplePath = "resources/sample-data/employees-sample.json"
}
