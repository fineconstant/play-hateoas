package repository.dbao

import java.util.UUID
import javax.inject.{Inject, Singleton}

import akka.NotUsed
import akka.stream.scaladsl.Source
import common.db.DDLOperations
import database.config.DatabaseProvider
import database.context.DatabaseExecutionContext
import models.Company
import repository.api.DBComponent
import repository.tables.CompaniesTable

import scala.concurrent.Future

@Singleton
class CompaniesDBAO @Inject()(protected val dbConfigProvider: DatabaseProvider)(implicit ec: DatabaseExecutionContext)
  extends DBComponent with CompaniesTable with DDLOperations {

  override val profile = dbConfigProvider.dbConfig.profile
  override val db = dbConfigProvider.dbConfig.db

  import profile.api._

  def insert(x: Company): Future[Int] = db.run(companies += x)

  def stream: Source[Company, NotUsed] = Source.fromPublisher(db.stream(companies.result))

  def findById(companyId: UUID): Future[Option[Company]] = {
    val query = companies.filter(_.id === companyId)
                .result
                .headOption
    db.run(query)
  }

  def save(x: Company): Future[Int] = db.run(companies += x)

  def save(xs: Seq[Company]): Future[AnyRef] = db.run(companies ++= xs)

  def delete(companyId: UUID): Future[Int] = {
    val query = companies.filter(_.id === companyId)
                .delete
    db.run(query)
  }

  def drop: Future[Int] = db.run(companies.delete)

  def createSchemaIfNotExists(): Future[Unit] = {
    val tableName = companies.baseTableRow.tableName
    val createSchemaAction = db.run(companies.schema.create)
    createSchemaIfNotExists(tableName, createSchemaAction, dbConfigProvider)
  }

  def dropTableIfExists(): Future[Unit] = {
    val tableName = companies.baseTableRow.tableName
    val dropTableAction = db.run(companies.schema.drop)
    dropTableIfExists(tableName, dropTableAction, dbConfigProvider)
  }

}
