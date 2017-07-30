package dao

import database.DatabaseSchema
import models.Person
import slick.basic.DatabasePublisher
import slick.jdbc.H2Profile.api._

class PeopleDAO(db: Database) extends DatabaseSchema {
  def indexPeople: DatabasePublisher[Person] = db.stream(people.result)
}
