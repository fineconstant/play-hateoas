package models

import java.time.LocalDate
import java.util.UUID

import play.api.libs.json.Json

case class Person(
  id: UUID = UUID.randomUUID,
  firstName: String,
  lastName: String,
  birthDate: LocalDate,
  companyId: UUID)

//object Person {
//  implicit val personFormat = Json.format[Person]
//}
