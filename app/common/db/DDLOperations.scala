package common.db

import akka.stream.alpakka.slick.scaladsl.SlickSession
import database.context.DatabaseExecutionContext
import play.Logger
import slick.jdbc.meta.MTable

import scala.concurrent.Future
import scala.util.{Failure, Success}


/**
  * Slick DDL operations examples
  */
trait DDLOperations {
  def createSchemaIfNotExists(tableName: String, schemaCreateAction: Future[Unit])
                             (implicit session: SlickSession, ec: DatabaseExecutionContext): Future[Unit] = {

    import session._

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
        Future.unit
      })
  }

  def dropTableIfExists(tableName: String, dropTableActions: Future[Unit])
                       (implicit session: SlickSession, ec: DatabaseExecutionContext): Future[Unit] = {

    import session._

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
        Future.unit
      })
  }

  private def tableExistsIn(table: String, dbTables: Vector[MTable]): Boolean =
    dbTables map (_.name.name) contains table

}
