package repository

import java.util.UUID
import javax.inject.{Inject, Singleton}

import models.{Company, Person}
import play.api.db.slick.DatabaseConfigProvider
import slick.basic.DatabasePublisher
import slick.jdbc.PostgresProfile
import slick.lifted.ProvenShape

import scala.concurrent.ExecutionContext

// TODO: externalize db config
@Singleton
class CompaniesRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  // We want the JdbcProfile for this provider
  private val dbConfig = dbConfigProvider.get[PostgresProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._

  /**
    * The starting point for all queries on the companies table.
    */
  private val companies = TableQuery[CompaniesTable]

  /** Table definition */
  class CompaniesTable(tag: Tag) extends Table[Company](tag, "COMPANIES") {
    /**
      * This is the tables default "projection".
      * It defines how the columns are converted to and from the Person object.
      **/
    override def * : ProvenShape[Company] = (id, name) <> (Company.tupled, Company.unapply)

    /** Primary key column definition */
    def id: Rep[UUID] = column[UUID]("ID", O.PrimaryKey)

    /** NAME column definition */
    def name: Rep[String] = column[String]("NAME")
  }

  def stream: DatabasePublisher[Company] = db.stream(companies.result)

  // TODO: implement
  def companiesWithPeople: DatabasePublisher[(Company, Person)] = {
    //val query = for {
    //  c <- companies
    //  p <- people if p.companyId === c.id
    //} yield (c, p)
    //
    //db.stream(query.result)
    ???
  }
}
