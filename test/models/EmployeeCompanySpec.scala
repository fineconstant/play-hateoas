package models

import base.BaseFlatSpec
import play.api.libs.json.{JsObject, JsString}

class EmployeeCompanySpec extends BaseFlatSpec {

  behavior of "employeeFormat"

  it should "read json value into object" in {
    val jsValue = JsObject(Seq(
      "firstName" -> JsString("foo"),
      "lastName" -> JsString("bar"),
      "companyName" -> JsString("baz")))

    val actual = EmployeeCompany.employeeCompanyFormat.reads(jsValue).get

    val expected = EmployeeCompany("foo", "bar", "baz")
    actual shouldBe expected
  }

  it should "write object to json value" in {
    val ec = EmployeeCompany("foo", "bar", "baz")

    val actual = EmployeeCompany.employeeCompanyFormat.writes(ec)

    val expected = JsObject(Seq(
      "firstName" -> JsString("foo"),
      "lastName" -> JsString("bar"),
      "companyName" -> JsString("baz")))
    actual shouldBe expected
  }

}
