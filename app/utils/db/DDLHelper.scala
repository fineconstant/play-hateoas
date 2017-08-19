package utils.db

import database.config.DatabaseProvider
import play.Logger
import slick.jdbc.meta.MTable

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

object DDLHelper {

  def createSchemaIfNotExists(
    tableName: String, schemaCreateAction: Future[Unit],
    dbConfigProvider: DatabaseProvider)
    (implicit ec: ExecutionContext): Future[Unit] = {

    import dbConfigProvider.dbConfig._

    Logger.info(s"Checking if table [$tableName] exists")

    db.run(MTable.getTables)
      .flatMap(tables => {
        if (!tableExistsIn(tableName, tables))
          schemaCreateAction
            .andThen {
              case Success(_) => Logger.info(s"Schema for table [$tableName] created")
              case Failure(f) => Logger.info(s"Could not create schema for table [$tableName]", f)
            }
        else {
          Logger.info(s"Schema for table [$tableName] already exists")
        }
        Future.successful()
      })
  }

  def dropTableIfExists(
    tableName: String, dropTableActions: Future[Unit],
    dbConfigProvider: DatabaseProvider)
    (implicit ec: ExecutionContext): Future[Unit] = {

    import dbConfigProvider.dbConfig._

    Logger.info(s"Checking if table [$tableName] exists")

    db.run(MTable.getTables)
      .flatMap(tables => {
        if (tableExistsIn(tableName, tables))
          dropTableActions
            .andThen {
              case Success(_) => Logger.info(s"Table [$tableName] dropped")
              case Failure(f) => Logger.info(s"Could not drop table [$tableName]", f)
            }
        else {
          Logger.info(s"Table [$tableName] does not exist")
        }
        Future.successful()
      })
  }

  private def tableExistsIn(table: String, dbTables: Vector[MTable]): Boolean =
    dbTables map (_.name.name) contains table

}
