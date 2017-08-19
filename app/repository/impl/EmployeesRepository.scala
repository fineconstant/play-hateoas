package repository.impl

import java.time.LocalDate
import java.util.UUID
import javax.inject.{Inject, Singleton}

import conversions.SlickConversions
import database.config.DatabaseProvider
import models.Employee
import repository.api.Repository
import slick.basic.DatabasePublisher
import slick.lifted.ProvenShape
import utils.db.DDLHelper

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class EmployeesRepository @Inject()(protected val dbConfigProvider: DatabaseProvider)
  (implicit ec: ExecutionContext) extends Repository[Employee] {

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfigProvider.dbConfig._
  import profile.api._

  // LocalDate conversion
  import SlickConversions._

  private val people = TableQuery[EmployeesTable]

  class EmployeesTable(tag: Tag) extends Table[Employee](tag, "EMPLOYEES") {
    override def * : ProvenShape[Employee] =
      (id, firstName, lastName, birthDate, companyId) <> ((Employee.apply _).tupled, Employee.unapply)

    def id: Rep[UUID] = column[UUID]("ID", O.PrimaryKey)

    def firstName: Rep[String] = column[String]("FIRST_NAME")

    def lastName: Rep[String] = column[String]("LAST_NAME")

    // conversion for LocalDate (import SlickConversions._) must be specified
    def birthDate: Rep[LocalDate] = column[LocalDate]("BIRTH_DATE")

    def companyId: Rep[UUID] = column[UUID]("COMPANY_ID")

    // TODO: relation
    //def company: ForeignKeyQuery[Companies, Company] = foreignKey("FK_COMPANY", companyId, companies)(_.id)
  }

  override def stream: DatabasePublisher[Employee] = db.stream(people.result)

  override def save(x: Employee): Future[Int] = db.run(people += x)

  override def save(xs: Seq[Employee]): Future[AnyRef] = db.run(people ++= xs)

  override def drop: Future[Int] = db.run(people.delete)

  override def createSchemaIfNotExists(): Future[Unit] = {
    val tableName = people.baseTableRow.tableName
    val createSchemaAction = db.run(people.schema.create)
    DDLHelper.createSchemaIfNotExists(tableName, createSchemaAction, dbConfigProvider)
  }

  override def dropTableIfExists(): Future[Unit] = {
    val tableName = people.baseTableRow.tableName
    val dropTableAction = db.run(people.schema.drop)
    DDLHelper.dropTableIfExists(tableName, dropTableAction, dbConfigProvider)
  }

}
