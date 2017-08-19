package utils.io

import models.Company
import play.api.libs.json.{JsValue, Json, Reads}

import scala.io.Source

object JsonFileReader {

  def read(relativePath: String): JsValue = {
    val source = Source.fromFile(relativePath)
    Json.parse(source.mkString)
  }

  def read[T](relativePath: String)(implicit reads: Reads[T]): Iterable[Company] = {
    val source = Source.fromFile(relativePath)
    Json.parse(source.mkString)
      .as[Iterable[Company]]
  }
}
