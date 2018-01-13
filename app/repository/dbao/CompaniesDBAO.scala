package repository.dbao

import java.util.UUID
import javax.inject.{Inject, Singleton}

import akka.NotUsed
import akka.stream.alpakka.slick.scaladsl._
import akka.stream.scaladsl.Source
import common.db.DDLOperations
import database.context.DatabaseExecutionContext
import database.provider.api.ApplicationDatabaseProvider
import models.Company
import repository.api.JDBCAware
import repository.tables.CompaniesTable

import scala.concurrent.Future

@Singleton
class CompaniesDBAO @Inject()(protected val dbProvider: ApplicationDatabaseProvider)
  (implicit ec: DatabaseExecutionContext) extends JDBCAware with DDLOperations with CompaniesTable {

  override val profile = dbProvider.profile
  override val db = dbProvider.db

  implicit override val session: SlickSession = dbProvider.session

  import profile.api._

  def insert(x: Company): Future[Int] = db.run(companies += x)

  // TODO: test
  def replaceWithNew(xs: Seq[Company]): Future[Option[Int]] = {
    val actions = DBIO.seq(companies.delete).andThen(companies ++= xs)
    db.run(actions)
  }

  def stream: Source[Company, NotUsed] = Slick.source(companies.result)

  def findById(companyId: UUID): Future[Option[Company]] = {
    val query = companies.filter(_.id === companyId)
                .result
                .headOption

    db.run(query)
  }

  def save(x: Company): Future[Int] = db.run(companies += x)

  def save(xs: Seq[Company]): Future[AnyRef] = db.run(companies ++= xs)

  // TODO: test
  def update(x: Company): Future[Int] = db.run(companies.update(x))

  // TODO: test
  def insertOrUpdate(x: Company): Future[Int] = db.run(companies.insertOrUpdate(x))

  def delete(companyId: UUID): Future[Int] = {
    val query = companies.filter(_.id === companyId)
                .delete
    db.run(query)
  }

  // TODO: test
  def prune: Future[Int] = db.run(companies.delete)

  def drop: Future[Int] = db.run(companies.delete)

  def createSchemaIfNotExists(): Future[Unit] = {
    val tableName = companies.baseTableRow.tableName
    val createSchemaAction = db.run(companies.schema.create)
    createSchemaIfNotExists(tableName, createSchemaAction)
  }

  def dropTableIfExists(): Future[Unit] = {
    val tableName = companies.baseTableRow.tableName
    val dropTableAction = db.run(companies.schema.drop)
    dropTableIfExists(tableName, dropTableAction)
  }

}
