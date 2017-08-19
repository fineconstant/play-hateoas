package models

import java.time.LocalDate
import java.util.UUID

case class Employee(
  id: UUID = UUID.randomUUID,
  firstName: String,
  lastName: String,
  birthDate: LocalDate,
  companyId: UUID)
