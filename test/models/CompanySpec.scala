package models

import java.util.UUID

import base.BaseFlatSpec
import com.google.gson.JsonObject
import play.api.libs.json.{JsObject, JsString, JsValue}

class CompanySpec extends BaseFlatSpec {
  behavior of "companyFormat"

  it should "read json value into object" in {
    val uuid = UUID.randomUUID()
    val jsValue = JsObject(Seq("id" -> JsString(uuid.toString), "name" -> JsString("foo")))

    val actual = Company.companyFormat.reads(jsValue).get

    val expected = Company(uuid, "foo")
    actual shouldBe expected
  }

  it should "write object to json value" in {
    val uuid = UUID.randomUUID()
    val company = Company(uuid, "foo")

    val actual = Company.companyFormat.writes(company)

    val expected = JsObject(Seq("id" -> JsString(uuid.toString), "name" -> JsString("foo")))
    actual shouldBe expected
  }

}
