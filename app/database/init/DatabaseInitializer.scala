package database.init

import javax.inject.Inject

import lifecycle.Initializable

final class DatabaseInitializer @Inject()(ops: InitialDatabaseOperations) extends Initializable {

  ops.dropAllTablesIfExist()
  ops.createSchemaIfNotExists()
  ops.initializeWithSampleData()

  //seed.companies.stream
  //  .foreach(c => Logger.info(s"Found company: $c"))
  //
  //seed.people.stream
  //  .foreach(c => Logger.info(s"Found person: $c"))
  //
  //seed.companies.withEmployees
  //  .foreach(c => Logger.info(s"Found company with people: $c"))


}
