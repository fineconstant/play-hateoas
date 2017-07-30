package dao

import database.DatabaseSchema
import models.{Company, Person}
import slick.basic.DatabasePublisher
import slick.jdbc.H2Profile.api._

class CompanyDAO(db: Database) extends DatabaseSchema {
  def indexCompanies: DatabasePublisher[Company] = db.stream(companies.result)

  def companiesWithPeople: DatabasePublisher[(Company, Person)] = {
    val query = for {
      c <- companies
      p <- people if p.companyId === c.id
    } yield (c, p)

    db.stream(query.result)
  }

}
