package repository

import javax.inject.{Inject, Singleton}

import database.config.DatabaseProvider
import models.{Company, Employee}
import repository.api.Repository
import repository.tables.CompaniesTable
import slick.basic.DatabasePublisher
import utils.db.DDLHelper

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CompaniesRepository @Inject()(protected val dbConfigProvider: DatabaseProvider)(implicit ec: ExecutionContext)
  extends Repository[Company] with CompaniesTable {

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
    DDLHelper.createSchemaIfNotExists(tableName, createSchemaAction, dbConfigProvider)
  }

  override def dropTableIfExists(): Future[Unit] = {
    val tableName = companies.baseTableRow.tableName
    val dropTableAction = db.run(companies.schema.drop)
    DDLHelper.dropTableIfExists(tableName, dropTableAction, dbConfigProvider)
  }

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
