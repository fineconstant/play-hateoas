package dao

import database.schema.DatabaseSchema
import models.Person
import slick.basic.DatabasePublisher
import slick.jdbc.PostgresProfile.api._

class PeopleDAO(db: Database) extends DatabaseSchema {
  def indexPeople: DatabasePublisher[Person] = db.stream(people.result)
}
