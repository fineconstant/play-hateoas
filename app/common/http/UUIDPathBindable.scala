package common.http

import java.util.UUID

import play.api.mvc.PathBindable

// TODO: use [[utils.UUIDPathBindable]] instead of a implicit conversion in [[routers.ApiRouter]]
object UUIDPathBindable {
  implicit def bindable: PathBindable[UUID] = new PathBindable[UUID] {
    override def bind(key: String, value: String): Either[String, UUID] = Right(UUID.fromString(value))

    override def unbind(key: String, id: UUID): String = id.toString
  }
}
