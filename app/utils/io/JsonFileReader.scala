package utils.io

import play.api.libs.json.{JsValue, Json, Reads}

import scala.io.Source
import scala.language.postfixOps

object JsonFileReader {

  def read(relativePath: String): JsValue = {
    val source = Source.fromFile(relativePath)
    Json.parse(source.mkString)
  }

  def read[T](relativePath: String)(implicit reads: Reads[T]): Seq[T] = {
    val source = Source fromFile relativePath mkString

    Json.parse(source)
      .as[Seq[T]]
  }
}
