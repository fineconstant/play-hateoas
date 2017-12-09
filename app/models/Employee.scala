package models

import java.time.LocalDate
import java.util.UUID

import play.api.libs.json.{Json, OFormat}

case class Employee(
  id: UUID,
  firstName: String,
  lastName : String,
  birthDate: LocalDate,
  companyId: UUID
)

object Employee {
  implicit val employeeFormat: OFormat[Employee] = Json.format[Employee]
}
