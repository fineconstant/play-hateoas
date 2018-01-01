package common.validators

import java.util.UUID

import play.api.Logger

object SanitizedUUID {
  def apply(id: UUID): UUID = {
    val sanitizedId = UUID fromString id.toString
    Logger.info(s"Sanitized UUID: [$sanitizedId]")

    sanitizedId
  }
}