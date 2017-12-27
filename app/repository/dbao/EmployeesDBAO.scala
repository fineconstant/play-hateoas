package repository.dbao

import javax.inject.{Inject, Singleton}

import akka.NotUsed
import akka.stream.scaladsl.Source
import common.db.DDLOperations
import database.config.DatabaseProvider
import database.context.DatabaseExecutionContext
import models.{Employee, EmployeeCompany}
import repository.api.DBComponent
import repository.tables.EmployeesTable
import slick.basic.DatabasePublisher

import scala.concurrent.Future

@Singleton
class EmployeesDBAO @Inject()(protected val dbConfigProvider: DatabaseProvider)(implicit ec: DatabaseExecutionContext)
  extends DBComponent with EmployeesTable with DDLOperations {

  val profile = dbConfigProvider.dbConfig.profile
  val db = dbConfigProvider.dbConfig.db

  import profile.api._

  def stream: Source[Employee, NotUsed] = Source.fromPublisher(db.stream(employees.result))

  def save(x: Employee): Future[Int] = db.run(employees += x)

  def save(xs: Seq[Employee]): Future[AnyRef] = db.run(employees ++= xs)

  def drop: Future[Int] = db.run(employees.delete)

  def createSchemaIfNotExists(): Future[Unit] = {
    val tableName = employees.baseTableRow.tableName
    val createSchemaAction = db.run(employees.schema.create)
    createSchemaIfNotExists(tableName, createSchemaAction, dbConfigProvider)
  }

  def dropTableIfExists(): Future[Unit] = {
    val tableName = employees.baseTableRow.tableName
    val dropTableAction = db.run(employees.schema.drop)
    dropTableIfExists(tableName, dropTableAction, dbConfigProvider)
  }

  def employedTuple: DatabasePublisher[(String, String, String)] = {
    val query = for {
      e <- employees
      c <- e.company
    } yield (e.firstName, e.lastName, c.name)

    db stream query.result
  }

  def employedCaseClass: DatabasePublisher[EmployeeCompany] = {
    val query = for {
      (e, c) <- employees join companies on (_.companyId === _.id)

    } yield (e.firstName, e.lastName, c.name)

    db.stream(query.result)
    .mapResult((EmployeeCompany.apply _).tupled)
  }

}
