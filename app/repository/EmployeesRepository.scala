package repository

import java.time.LocalDate
import java.util.UUID
import javax.inject.{Inject, Singleton}

import conversions.SlickConversions
import database.config.ApplicationDatabaseConfigProvider
import models.{Company, Employee}
import play.db.NamedDatabase
import play.api.db.slick.DatabaseConfigProvider
import slick.basic.DatabasePublisher
import slick.jdbc.PostgresProfile
import slick.lifted.{ForeignKeyQuery, ProvenShape}

import scala.concurrent.ExecutionContext

@Singleton
class EmployeesRepository @Inject()(protected val dbConfigProvider: ApplicationDatabaseConfigProvider)
  (implicit ec: ExecutionContext) {

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

  def stream: DatabasePublisher[Employee] = db.stream(people.result)
}
