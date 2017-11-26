package models

import play.api.libs.json.{Json, OFormat, OWrites, Reads}

case class EmployeeCompany(
  firstName  : String,
  lastName   : String,
  companyName: String)

object EmployeeCompany {
  implicit val employeeWrites: OWrites[EmployeeCompany] = Json.writes[EmployeeCompany]
  implicit val employeeReads: Reads[EmployeeCompany] = Json.reads[EmployeeCompany]
  implicit val employeeFormat: OFormat[EmployeeCompany] = Json.format[EmployeeCompany]
}
