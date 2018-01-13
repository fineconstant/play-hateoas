package repository.dbao

import javax.inject.{Inject, Singleton}

import akka.NotUsed
import akka.stream.alpakka.slick.scaladsl.SlickSession
import akka.stream.scaladsl.Source
import common.db.DDLOperations
import database.context.DatabaseExecutionContext
import database.provider.api.ApplicationDatabaseProvider
import models.{Employee, EmployeeCompany}
import repository.api.JDBCAware
import repository.tables.EmployeesTable
import slick.basic.DatabasePublisher

import scala.concurrent.Future

@Singleton
class EmployeesDBAO @Inject()(protected val dbProvider: ApplicationDatabaseProvider)
  (implicit ec: DatabaseExecutionContext) extends JDBCAware with DDLOperations with EmployeesTable {

  override val profile = dbProvider.profile
  override val db = dbProvider.db

  implicit override val session: SlickSession = dbProvider.session

  import profile.api._

  def stream: Source[Employee, NotUsed] = Source.fromPublisher(db.stream(employees.result))

  def save(x: Employee): Future[Int] = db.run(employees += x)

  def save(xs: Seq[Employee]): Future[AnyRef] = db.run(employees ++= xs)

  def drop: Future[Int] = db.run(employees.delete)

  def createSchemaIfNotExists(): Future[Unit] = {
    val tableName = employees.baseTableRow.tableName
    val createSchemaAction = db.run(employees.schema.create)
    createSchemaIfNotExists(tableName, createSchemaAction)
  }

  def dropTableIfExists(): Future[Unit] = {
    val tableName = employees.baseTableRow.tableName
    val dropTableAction = db.run(employees.schema.drop)
    dropTableIfExists(tableName, dropTableAction)
  }

  def employedCaseClass: DatabasePublisher[EmployeeCompany] = {
    val query = for {
      (e, c) <- employees join companies on (_.companyId === _.id)

    } yield (e.firstName, e.lastName, c.name)

    db.stream(query.result)
    .mapResult((EmployeeCompany.apply _).tupled)
  }

}
