package models

import java.util.UUID

case class Company(id: UUID = UUID.randomUUID, name: String)

//object Company {
//  implicit val personFormat = Json.format[Company]
//}
