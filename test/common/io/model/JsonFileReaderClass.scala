package common.io.model

import play.api.libs.json.{Json, OFormat}


private[io] case class JsonFileReaderClass(
  singleValue: String,
  arrayValue : Seq[Int]
)

private[io] case object JsonFileReaderClass {
  val jfrcFormat: OFormat[JsonFileReaderClass] = Json.format[JsonFileReaderClass]
}
