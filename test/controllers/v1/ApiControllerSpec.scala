package controllers.v1

import base.BaseFlatSpec
import play.api.test.FakeRequest
import play.api.test.Helpers._

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

class ApiControllerSpec extends BaseFlatSpec {
  implicit val ec: ExecutionContextExecutor = ExecutionContext.global

  "stream" should "be OK" in {
    val controller = new ApiController(null, null, stubControllerComponents())

    val result = controller.stream.apply(FakeRequest())

    val actual = contentAsString(result)
    val expected = "OK"
    actual shouldBe expected
  }
}
