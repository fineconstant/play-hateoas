package controllers.v1

import base.BaseFlatSpec
import play.api.test.FakeRequest
import play.api.test.Helpers.{contentAsString, stubControllerComponents, _}

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

class EmployeesControllerSpec extends BaseFlatSpec {
  implicit val ec: ExecutionContextExecutor = ExecutionContext.global

  "employees" should "be OK" in {
    val controller = new EmployeesController(stubControllerComponents(), null)

    val result = controller.employees.apply(FakeRequest())

    val actual = contentAsString(result)
    val expected = "/employees"
    actual shouldBe expected
  }

}
