package models

import java.util.UUID

case class Company(id: UUID = UUID.randomUUID, name: String)
