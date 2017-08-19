package conversions

import models.{Company, Employee}
import play.api.libs.json.{Json, OFormat, OWrites, Reads}

object JsonConversions {

  object Company {
    implicit val companyWrites: OWrites[Company] = Json.writes[Company]
    implicit val companyReads: Reads[Company] = Json.reads[Company]
    implicit val companyFormat: OFormat[Company] = Json.format[Company]
  }

  object Employee {
    implicit val employeeWrites: OWrites[Employee] = Json.writes[Employee]
    implicit val employeeReads: Reads[Employee] = Json.reads[Employee]
    implicit val employeeFormat: OFormat[Employee] = Json.format[Employee]
  }

}
