package models

import java.util.UUID

import play.api.libs.json.{Json, OFormat, OWrites, Reads}

case class Company(id: UUID = UUID.randomUUID, name: String)

object Company {
  implicit val companyWrites: OWrites[Company] = Json.writes[Company]
  implicit val companyReads: Reads[Company] = Json.reads[Company]
  implicit val companyFormat: OFormat[Company] = Json.format[Company]
}
