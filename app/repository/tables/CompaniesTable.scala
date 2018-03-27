package repository.tables

import java.util.UUID

import models.Company
import repository.api.JDBCAware
import slick.lifted.ProvenShape


private[repository] trait CompaniesTable {
  this: JDBCAware =>

  import profile.api._


  /**
    * The starting point for all queries on the companies table.
    */
  private[repository] val companies = TableQuery[CompaniesTable]

  /** Table definition */
  class CompaniesTable(tag: Tag) extends Table[Company](tag, "COMPANIES") {
    /**
      * This is the tables default "projection".
      * It defines how the columns are converted to and from the Person object.
      *
      * (Company.apply _).tupled is used instead of Company.tupled because of case class' companion objects with conversions
      **/
    override def * : ProvenShape[Company] = (id, name) <> ((Company.apply _).tupled, Company.unapply)

    /** Primary key column definition */
    def id: Rep[UUID] = column[UUID]("ID", O.PrimaryKey)

    /** NAME column definition */
    def name: Rep[String] = column[String]("NAME")
  }

}
