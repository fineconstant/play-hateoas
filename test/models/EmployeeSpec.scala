package models

import java.time.LocalDate
import java.util.UUID

import base.BaseFlatSpec
import play.api.libs.json._

class EmployeeSpec extends BaseFlatSpec {
  behavior of "employeeFormat"

  it should "read json value into object" in {
    val uuid = UUID.randomUUID()
    val jsValue = JsObject(Seq(
      "id" -> JsString(uuid.toString),
      "firstName" -> JsString("foo"),
      "lastName" -> JsString("bar"),
      "birthDate" -> JsString(LocalDate.of(2000, 1, 1).toString),
      "companyId" -> JsString(uuid.toString)))

    val actual = Employee.employeeFormat.reads(jsValue).get

    val expected = Employee(uuid, "foo", "bar", LocalDate.of(2000, 1, 1), uuid)
    actual shouldBe expected
  }

  it should "write object to json value" in {
    val uuid = UUID.randomUUID()
    val employee = Employee(uuid, "foo", "bar", LocalDate.of(2000, 1, 1), uuid)

    val actual = Employee.employeeFormat.writes(employee)

    val expected = JsObject(Seq(
      "id" -> JsString(uuid.toString),
      "firstName" -> JsString("foo"),
      "lastName" -> JsString("bar"),
      "birthDate" -> JsString(LocalDate.of(2000, 1, 1).toString),
      "companyId" -> JsString(uuid.toString)))
    actual shouldBe expected
  }

}
