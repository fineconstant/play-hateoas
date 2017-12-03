package common.io

import java.io.File

import play.api.libs.json.{JsValue, Json, Reads}

import scala.io.Source
import scala.language.postfixOps

object JsonFileReader {
  private val ExceptionMessage = "Directory traversal attempt - absolute path not allowed!"

  def read(relativePath: String): JsValue = {
    validatePath(relativePath)

    val source = Source fromFile relativePath
    Json.parse(source.mkString)
  }

  def read[T](relativePath: String)(implicit reads: Reads[T]): Seq[T] = {
    validatePath(relativePath)

    val source = Source fromFile relativePath mkString

    Json.parse(source)
      .as[Seq[T]]
  }

  private def isAbsolute(relativePath: String) = {
    new File(relativePath).isAbsolute
  }

  private def validatePath(relativePath: String): Unit = {
    if (isAbsolute(relativePath))
      throw new RuntimeException(ExceptionMessage)
  }
}
