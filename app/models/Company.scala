package models

import java.util.UUID

import play.api.libs.json.{Json, OFormat}

case class Company(id: UUID, name: String)

object Company {
  implicit val companyFormat: OFormat[Company] = Json.format[Company]
}
