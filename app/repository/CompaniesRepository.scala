package repository

import javax.inject.{Inject, Singleton}

import common.db.DDLOperations
import database.config.DatabaseProvider
import database.context.DatabaseExecutionContext
import models.Company
import repository.api.Repository
import repository.tables.CompaniesTable
import slick.basic.DatabasePublisher

import scala.concurrent.Future

@Singleton
class CompaniesRepository @Inject()(
  protected val dbConfigProvider: DatabaseProvider)(implicit ec: DatabaseExecutionContext)
  extends Repository[Company] with CompaniesTable with DDLOperations {

  override val profile = dbConfigProvider.dbConfig.profile
  override val db = dbConfigProvider.dbConfig.db

  import profile.api._

  override def stream: DatabasePublisher[Company] = db.stream(companies.result)

  override def save(x: Company): Future[Int] = db.run(companies += x)

  override def save(xs: Seq[Company]): Future[AnyRef] = db.run(companies ++= xs)

  override def drop: Future[Int] = db.run(companies.delete)

  override def createSchemaIfNotExists(): Future[Unit] = {
    val tableName = companies.baseTableRow.tableName
    val createSchemaAction = db.run(companies.schema.create)
    createSchemaIfNotExists(tableName, createSchemaAction, dbConfigProvider)
  }

  override def dropTableIfExists(): Future[Unit] = {
    val tableName = companies.baseTableRow.tableName
    val dropTableAction = db.run(companies.schema.drop)
    dropTableIfExists(tableName, dropTableAction, dbConfigProvider)
  }

}
