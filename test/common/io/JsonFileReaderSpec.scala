package common.io

import base.BaseFlatSpec
import common.io.model.JsonFileReaderClass
import play.api.libs.json._

class JsonFileReaderSpec extends BaseFlatSpec {

  trait TestResource {
    val resource = getClass.getResource("/resources/json-file-reader-spec.json")
  }

  behavior of "read"

  it should "read from absolute path" in new TestResource {
    val actual: JsValue = JsonFileReader.read(resource.getPath)

    println(s"PATH>>>[${resource.getPath}]")

    val expected = Json
      .parse(
        """[
          |  {
          |    "singleValue": "foo",
          |    "arrayValue": [
          |      1,
          |      2,
          |      3
          |    ]
          |  }
          |]
        """.stripMargin)

    actual shouldBe expected
  }

  it should "fail to read from relative path" in new TestResource {
    val expectedMessage = "Directory traversal attempt - absolute path not allowed!"

    the[RuntimeException] thrownBy {
      JsonFileReader.read("resources/json-file-reader-spec.json")
    } should have message expectedMessage
  }


  behavior of "read typed"

  it should "read from absolute path" in new TestResource {
    implicit val format = JsonFileReaderClass.jfrcFormat

    val actual = JsonFileReader.readTyped[JsonFileReaderClass](resource.getPath)

    val expected = Seq(JsonFileReaderClass("foo",Seq(1,2,3)))
    actual shouldBe expected
  }

  it should "fail to read from relative path" in new TestResource {
    implicit val format = JsonFileReaderClass.jfrcFormat
    val expectedMessage = "Directory traversal attempt - absolute path not allowed!"

    the[RuntimeException] thrownBy {
      JsonFileReader.readTyped[JsonFileReaderClass]("resources/json-file-reader-spec.json")
    } should have message expectedMessage
  }


  behavior of "isAbsolute"

  it should "tell that path is absolute" in {
    val isAbsolute = PrivateMethod[Boolean]('isAbsolute)
    val absolutePath = "/home/test"

    val actual = JsonFileReader invokePrivate isAbsolute(absolutePath)

    val expected = true

    actual shouldBe expected
  }

  it should "tell that path is relative" in {
    val isAbsolute = PrivateMethod[Boolean]('isAbsolute)
    val absolutePath = "../test"

    val actual = JsonFileReader invokePrivate isAbsolute(absolutePath)

    val expected = false

    actual shouldBe expected
  }


  behavior of "validatePath"

  it should "throw an exception if the path is relative" in {
    val validatePath = PrivateMethod[Boolean]('validatePath)
    val absolutePath = "../../test"

    val expectedMessage = "Directory traversal attempt - absolute path not allowed!"

    the[RuntimeException] thrownBy {
      JsonFileReader invokePrivate validatePath(absolutePath)
    } should have message expectedMessage
  }

  it should "not throw any exception it path is absolute" in {
    val validatePath = PrivateMethod[Boolean]('validatePath)
    val absolutePath = "/home/test"


    JsonFileReader invokePrivate validatePath(absolutePath)
    succeed
  }
}
