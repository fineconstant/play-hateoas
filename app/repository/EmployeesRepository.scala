package repository

import javax.inject.{Inject, Singleton}

import database.config.DatabaseProvider
import models.{Company, Employee, EmployeeCompany}
import repository.api.Repository
import repository.tables.EmployeesTable
import slick.basic.DatabasePublisher
import utils.db.DDLHelper

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class EmployeesRepository @Inject()(protected val dbConfigProvider: DatabaseProvider)(implicit ec: ExecutionContext)
  extends Repository[Employee] with EmployeesTable {

  override val profile = dbConfigProvider.dbConfig.profile
  override val db = dbConfigProvider.dbConfig.db

  import profile.api._

  override def stream: DatabasePublisher[Employee] = db.stream(employees.result)

  override def save(x: Employee): Future[Int] = db.run(employees += x)

  override def save(xs: Seq[Employee]): Future[AnyRef] = db.run(employees ++= xs)

  override def drop: Future[Int] = db.run(employees.delete)

  override def createSchemaIfNotExists(): Future[Unit] = {
    val tableName = employees.baseTableRow.tableName
    val createSchemaAction = db.run(employees.schema.create)
    DDLHelper.createSchemaIfNotExists(tableName, createSchemaAction, dbConfigProvider)
  }

  override def dropTableIfExists(): Future[Unit] = {
    val tableName = employees.baseTableRow.tableName
    val dropTableAction = db.run(employees.schema.drop)
    DDLHelper.dropTableIfExists(tableName, dropTableAction, dbConfigProvider)
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
