package dao

import database.DatabaseSchema
import slick.jdbc.H2Profile.api._

import scala.concurrent.Future

class CompanyDAO(db: Database) extends DatabaseSchema {
  def companiesWithPeople: Future[Seq[(String, String, String)]] = {
    val query = for {
      c <- companies
      p <- people if c.id === p.companyId
    } yield (c.name, p.firstName, p.lastName)

    //query.result.statements.foreach(println)
    db.run(query.result)
  }

}
