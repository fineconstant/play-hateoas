package models

import java.time.LocalDate
import java.util.UUID

import play.api.libs.json.{Json, OFormat, OWrites, Reads}

case class Employee(
  id       : UUID = UUID.randomUUID,
  firstName: String,
  lastName : String,
  birthDate: LocalDate,
  companyId: UUID
)

object Employee {
  implicit val employeeWrites: OWrites[Employee] = Json.writes[Employee]
  implicit val employeeReads: Reads[Employee] = Json.reads[Employee]
  implicit val employeeFormat: OFormat[Employee] = Json.format[Employee]
}
