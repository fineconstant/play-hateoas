package services

import java.util.UUID

import akka.stream.scaladsl.Source
import akka.stream.testkit.scaladsl.TestSink
import akka.testkit.TestKit
import base.{AkkaTestKit, BaseFlatSpec}
import models.Company
import play.api.libs.json.{JsValue, Json}
import repository.CompaniesRepository
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}


class CompaniesServiceSpec extends AkkaTestKit with BaseFlatSpec {

  override def afterAll() {
    TestKit.shutdownActorSystem(system)
  }


  behavior of "companiesJson"

  it should "contain JSON array with no objects" in {
    val companies = List.empty
    val source = Source(companies)

    val mockRepo = mock[CompaniesRepository]
    mockRepo.listAll _ expects() returns source
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
    mockRepo.listAll _ expects() returns source
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
    mockRepo.listAll _ expects() returns source
    val service = new CompaniesService(mockRepo)

    // when
    val actual = service.companiesJson

    // then
    actual.runWith(TestSink.probe[JsValue])
    .request(1)
    .expectNext(Json.toJson(companies))
    .expectComplete()
  }


  behavior of "findById"

  it should "find the requested company and return it in Some" in {
    // given
    import scala.concurrent.ExecutionContext.Implicits.global
    val uuid = UUID.randomUUID()
    val company = Company(uuid, "company 1")

    val mockRepo = mock[CompaniesRepository]
    mockRepo.findById _ expects uuid returns Future(Some(company))
    val service = new CompaniesService(mockRepo)

    // when
    val actualFuture = service.findById(uuid)
    val actual = Await.result(actualFuture, 1.second)
    val expected = company

    // then
    actual.value shouldBe expected
  }

  it should "not find any companies and return None" in {
    // given
    import scala.concurrent.ExecutionContext.Implicits.global
    val uuid = UUID.randomUUID()

    val mockRepo = mock[CompaniesRepository]
    mockRepo.findById _ expects uuid returns Future(None)
    val service = new CompaniesService(mockRepo)

    // when
    val actualFuture = service.findById(uuid)
    val actual = Await.result(actualFuture, 1.second)

    // then
    actual shouldBe None
  }


  behavior of "deleteById"

  it should "delete the requested company and return num of affected rows" in {
    // given
    import scala.concurrent.ExecutionContext.Implicits.global
    val uuid = UUID.randomUUID()

    val mockRepo = mock[CompaniesRepository]
    mockRepo.deleteById _ expects uuid returns Future(1)
    val service = new CompaniesService(mockRepo)

    // when
    val actualFuture = service.deleteById(uuid)
    val actual = Await.result(actualFuture, 1.second)
    val expected = 1

    // then
    actual shouldBe expected
  }

  it should "not find a requested company and not delete anything" in {
    // given
    import scala.concurrent.ExecutionContext.Implicits.global
    val uuid = UUID.randomUUID()

    val mockRepo = mock[CompaniesRepository]
    mockRepo.deleteById _ expects uuid returns Future(0)
    val service = new CompaniesService(mockRepo)

    // when
    val actualFuture = service.deleteById(uuid)
    val actual = Await.result(actualFuture, 1.second)
    val expected = 0

    // then
    actual shouldBe expected
  }


  behavior of "createCompany"

  it should "should create company and pass the number of affected objects" in {
    // given
    import scala.concurrent.ExecutionContext.Implicits.global
    val company = Company(UUID.randomUUID(), "company 1")

    val mockRepo = mock[CompaniesRepository]
    mockRepo.create _ expects company returns Future(1)
    val service = new CompaniesService(mockRepo)

    // when
    val actualFuture = service.createCompany(company)
    val actual = Await.result(actualFuture, 1.second)
    val expected = 1

    // then
    actual shouldBe expected
  }

  it should "should not create company and return 0" in {
    // given
    import scala.concurrent.ExecutionContext.Implicits.global
    val company = Company(UUID.randomUUID(), "company 1")

    val mockRepo = mock[CompaniesRepository]
    mockRepo.create _ expects company returns Future(0)
    val service = new CompaniesService(mockRepo)

    // when
    val actualFuture = service.createCompany(company)
    val actual = Await.result(actualFuture, 1.second)
    val expected = 0

    // then
    actual shouldBe expected
  }

}
