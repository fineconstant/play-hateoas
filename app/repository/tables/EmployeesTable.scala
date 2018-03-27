package repository.tables

import java.time.LocalDate
import java.util.UUID

import conversions.SlickConversions
import models.{Company, Employee}
import repository.api.JDBCAware
import slick.lifted.{ForeignKeyQuery, ProvenShape}


private[repository] trait EmployeesTable extends CompaniesTable {
  this: JDBCAware =>

  import profile.api._

  // LocalDate conversion
  import SlickConversions._


  private[repository] val employees = TableQuery[EmployeesTable]

  class EmployeesTable(tag: Tag) extends Table[Employee](tag, "EMPLOYEES") {
    override def * : ProvenShape[Employee] =
      (id, firstName, lastName, birthDate, companyId) <> ((Employee.apply _).tupled, Employee.unapply)

    def id: Rep[UUID] = column[UUID]("ID", O.PrimaryKey)

    def firstName: Rep[String] = column[String]("FIRST_NAME")

    def lastName: Rep[String] = column[String]("LAST_NAME")

    // conversion for LocalDate (import SlickConversions._) must be specified
    def birthDate: Rep[LocalDate] = column[LocalDate]("BIRTH_DATE")

    def companyId: Rep[UUID] = column[UUID]("COMPANY_ID")

    def company: ForeignKeyQuery[CompaniesTable, Company] = foreignKey("FK_COMPANY", companyId, companies)(_.id)
  }

}
