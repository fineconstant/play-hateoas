package models

import play.api.libs.json.{Json, OFormat}


case class EmployeeCompany(
                            firstName: String,
                            lastName: String,
                            companyName: String)

object EmployeeCompany {
  implicit val employeeCompanyFormat: OFormat[EmployeeCompany] = Json.format[EmployeeCompany]
}
