package utils.io

import java.io.File

import play.api.libs.json.{JsValue, Json, Reads}

import scala.io.Source
import scala.language.postfixOps

object JsonFileReader {

  def read(relativePath: String): JsValue = {
    if (new File(relativePath).isAbsolute)
      throw new RuntimeException("Directory traversal attempt - absolute path not allowed")

    val source = Source fromFile relativePath
    Json.parse(source.mkString)
  }

  def read[T](relativePath: String)(implicit reads: Reads[T]): Seq[T] = {
    if (new File(relativePath).isAbsolute)
      throw new RuntimeException("Directory traversal attempt - absolute path not allowed")

    val source = Source fromFile relativePath mkString

    Json.parse(source)
      .as[Seq[T]]
  }
}
