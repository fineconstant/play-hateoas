package models

import java.util.UUID

import play.api.libs.json.{Json, OWrites, Reads}

case class RestResource(id: UUID, name: String, link: String)

object RestResource {
  implicit val restResourceWrites: OWrites[RestResource] = Json.writes[RestResource]
  implicit val restResourceReads: Reads[RestResource] = Json.reads[RestResource]
}
