package database.init

import javax.inject.Inject

import lifecycle.Initializable

final class DatabaseInitializer @Inject()(ops: InitialDatabaseOperations) extends Initializable {
  ops.dropAllTablesIfExist()
  ops.createSchemaIfNotExists()
  ops.initializeWithSampleData()
}
