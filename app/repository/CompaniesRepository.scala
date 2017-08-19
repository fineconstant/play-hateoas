package repository

import java.util.UUID
import javax.inject.{Inject, Singleton}

import database.config.ApplicationDatabaseConfigProvider
import models.{Company, Employee}
import slick.basic.DatabasePublisher
import slick.lifted
import slick.lifted.ProvenShape

import scala.concurrent.ExecutionContext

@Singleton
class CompaniesRepository @Inject()(protected val dbConfigProvider: ApplicationDatabaseConfigProvider)
  (implicit ec: ExecutionContext) {

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfigProvider.dbConfig._
  import profile.api._

  /**
    * The starting point for all queries on the companies table.
    */
  private val companies = lifted.TableQuery[CompaniesTable]

  /** Table definition */
  class CompaniesTable(tag: Tag) extends Table[Company](tag, "COMPANIES") {
    /**
      * This is the tables default "projection".
      * It defines how the columns are converted to and from the Person object.
      *
      * Use (Company.apply _).tupled instead of Company.tupled because of case class' companion objects with conversions
      **/
    override def * : ProvenShape[Company] = (id, name) <> ((Company.apply _).tupled, Company.unapply)

    /** Primary key column definition */
    def id: Rep[UUID] = column[UUID]("ID", O.PrimaryKey)

    /** NAME column definition */
    def name: Rep[String] = column[String]("NAME")
  }

  def stream: DatabasePublisher[Company] = db.stream(companies.result)

  // TODO: implement
  def withEmployees: DatabasePublisher[(Company, Employee)] = {
    //val query = for {
    //  c <- companies
    //  p <- people if p.companyId === c.id
    //} yield (c, p)
    //
    //db.stream(query.result)
    ???
  }
}
