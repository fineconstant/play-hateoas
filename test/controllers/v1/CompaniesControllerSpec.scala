package controllers.v1

import base.BaseFlatSpec
import play.api.test.FakeRequest
import play.api.test.Helpers._
import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

class CompaniesControllerSpec extends BaseFlatSpec {
  implicit val ec: ExecutionContextExecutor = ExecutionContext.global

  "companies" should "be OK" in {
    val controller = new CompaniesController(stubControllerComponents(),null)

    val result = controller.companies.apply(FakeRequest())

    val actual = contentAsString(result)
    val expected = "/companies"
    actual shouldBe expected
  }

}
