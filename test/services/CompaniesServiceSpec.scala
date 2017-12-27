package services

import java.util.UUID

import akka.stream.scaladsl.Source
import akka.stream.testkit.scaladsl.TestSink
import akka.testkit.TestKit
import base.{AkkaTestKit, BaseFlatSpec}
import models.Company
import play.api.libs.json.{JsValue, Json}
import repository.CompaniesRepository


class CompaniesServiceSpec extends AkkaTestKit with BaseFlatSpec {

  override def afterAll() {
    TestKit.shutdownActorSystem(system)
  }

  behavior of "companiesJson"

  it should "contain JSON array with no objects" in {
    val companies = List.empty
    val source = Source(companies)

    val mockRepo = mock[CompaniesRepository]
    mockRepo.companies _ expects() returns source
    val service = new CompaniesService(mockRepo)

    // when
    val actual = service.companiesJson

    // then
    actual.runWith(TestSink.probe[JsValue])
    .request(1)
    .expectComplete()
  }

  it should "contain JSON array with 1 Company" in {
    val companies = List(Company(UUID.randomUUID(), "company 1"))
    val source = Source(companies)

    val mockRepo = mock[CompaniesRepository]
    mockRepo.companies _ expects() returns source
    val service = new CompaniesService(mockRepo)

    // when
    val actual = service.companiesJson

    // then
    actual.runWith(TestSink.probe[JsValue])
    .request(1)
    .expectNext(Json.toJson(companies))
    .expectComplete()
  }

  it should "contain JSON array with 2 Companies" in {
    // given
    val uuid = UUID.randomUUID()
    val companies = List(Company(uuid, "company 1"), Company(uuid, "company 2"))
    val source = Source(companies)

    val mockRepo = mock[CompaniesRepository]
    mockRepo.companies _ expects() returns source
    val service = new CompaniesService(mockRepo)

    // when
    val actual = service.companiesJson

    // then
    actual.runWith(TestSink.probe[JsValue])
    .request(1)
    .expectNext(Json.toJson(companies))
    .expectComplete()
  }


}
