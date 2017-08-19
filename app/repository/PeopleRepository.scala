package repository

import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

import conversions.SlickConversions
import models.{Company, Person}
import play.api.db.slick.DatabaseConfigProvider
import slick.basic.DatabasePublisher
import slick.jdbc.PostgresProfile
import slick.lifted.{ForeignKeyQuery, ProvenShape}

import scala.concurrent.ExecutionContext

@Singleton
class PeopleRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  // We want the JdbcProfile for this provider
  private val dbConfig = dbConfigProvider.get[PostgresProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._

  // LocalDate conversion
  import SlickConversions._

  private val people = TableQuery[PeopleTable]

  class PeopleTable(tag: Tag) extends Table[Person](tag, "PEOPLE") {
    override def * : ProvenShape[Person] =
      (id, firstName, lastName, birthDate, companyId) <> (Person.tupled, Person.unapply)

    def id: Rep[UUID] = column[UUID]("ID", O.PrimaryKey)

    def firstName: Rep[String] = column[String]("FIRST_NAME")

    def lastName: Rep[String] = column[String]("LAST_NAME")

    // conversion for LocalDate (import SlickConversions._) must be specified
    def birthDate: Rep[LocalDate] = column[LocalDate]("BIRTH_DATE")

    def companyId: Rep[UUID] = column[UUID]("COMPANY_ID")

    //def company: ForeignKeyQuery[Companies, Company] = foreignKey("FK_COMPANY", companyId, companies)(_.id)
  }

  def stream: DatabasePublisher[Person] = db.stream(people.result)
}
