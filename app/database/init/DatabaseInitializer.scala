package database.init

import javax.inject.Inject
import play.api.Logger
import lifecycle.Initializable

final class DatabaseInitializer @Inject()(ops: InitialDatabaseOperations) extends Initializable {
  val logger = Logger(this.getClass)
  logger.info("Initializing database")

  //  ops.dropAllTablesIfExist()
  //  ops.createSchemaIfNotExists()
  //  ops.initializeWithSampleData()
}
