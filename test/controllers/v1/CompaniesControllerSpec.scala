package controllers.v1

import java.util.UUID

import akka.stream.scaladsl.Source
import base.{AkkaTestKit, BaseFlatSpec}
import models.Company
import models.Company._
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._
import services.CompaniesService

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}

class CompaniesControllerSpec extends AkkaTestKit with BaseFlatSpec {
  implicit val ec: ExecutionContextExecutor = ExecutionContext.global

  "companies" should "return a company json with 200 code" in {
    // given
    val company = Company(UUID.randomUUID(), "Company name")
    val companies = List(company)
    val companiesJson = Json.toJson(companies)

    val jsSource = Source(List(companiesJson))

    val mockService = mock[CompaniesService]
    mockService.companiesJson _ expects() returns jsSource

    val controller = new CompaniesController(stubControllerComponents(), mockService)

    // when
    val result = controller.companies.apply(FakeRequest())

    // then
    val actual = contentAsJson(result)
    val expected = companiesJson
    actual shouldBe expected
  }


  behavior of "show"

  it should "return the company that is asked for" in {
    // given
    val uuid = UUID.randomUUID()
    val company = Company(uuid, "Company name")
    val companiesJson = Json.toJson(company)

    val mockService = mock[CompaniesService]
    mockService.findById _ expects uuid returns Future(Option(company))

    val controller = new CompaniesController(stubControllerComponents(), mockService)

    // when
    val result = controller.show(uuid).apply(FakeRequest())

    // then
    val actual = contentAsJson(result)
    val expected = companiesJson
    actual shouldBe expected
  }

  it should "return 404 not found when company is not found" in {
    // given
    val mockService = mock[CompaniesService]
    val requestUUID = UUID.randomUUID()
    mockService.findById _ expects requestUUID returns Future(None)

    val controller = new CompaniesController(stubControllerComponents(), mockService)

    // when
    val result = controller.show(requestUUID).apply(FakeRequest())

    // then
    status(result) shouldBe NOT_FOUND
  }


  behavior of "delete"

  it should "return the number of deleted objects" in {
    // given
    val uuid = UUID.randomUUID()

    val mockService = mock[CompaniesService]
    mockService.deleteById _ expects uuid returns Future(1)

    val controller = new CompaniesController(stubControllerComponents(), mockService)

    // when
    val result = controller.delete(uuid).apply(FakeRequest())

    // then
    val actual = contentAsString(result)
    val expected = "1"
    actual shouldBe expected
  }

  it should "return 404 requested to delete not existing company" in {
    // given
    val mockService = mock[CompaniesService]
    val requestUUID = UUID.randomUUID()
    mockService.deleteById _ expects requestUUID returns Future(0)

    val controller = new CompaniesController(stubControllerComponents(), mockService)

    // when
    val result = controller.delete(requestUUID).apply(FakeRequest())

    // then
    status(result) shouldBe NOT_FOUND
  }

}
