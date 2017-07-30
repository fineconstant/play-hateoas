package conversions

import models.Person
import play.api.libs.json.{Json, OWrites, Reads}

object JsonConversions {

  object Person {
    implicit val restResourceWrites: OWrites[Person] = Json.writes[Person]
    implicit val restResourceReads: Reads[Person] = Json.reads[Person]
  }

}
