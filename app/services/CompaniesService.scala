package services

import java.util.UUID
import javax.inject.{Inject, Singleton}

import akka.NotUsed
import akka.stream.scaladsl.Source
import models.Company
import play.api.libs.json.{JsValue, Json}
import repository.CompaniesRepository

import scala.concurrent.Future

@Singleton
class CompaniesService @Inject()(repository: CompaniesRepository) {

  def companiesJson: Source[JsValue, NotUsed] = repository.listAll
                                                .grouped(Int.MaxValue)
                                                .map(xs => Json.toJson(xs))

  def findById(id: UUID): Future[Option[Company]] = repository.findById(id)

  def replaceWithNew(companies: Seq[Company]): Future[Option[Int]] = repository.replaceWithNew(companies)

  def upsert(id: UUID, company: Company): Future[Int] =
    executeIfTrue(id == company.id, repository.upsert(company), Future(0))

  def update(id: UUID, company: Company): Future[Int] =
    executeIfTrue(id == company.id, repository.upsert(company), Future(0))

  def createCompany(company: Company): Future[Int] = repository.create(company)

  def clear: Future[Int] = repository.clear

  def deleteById(id: UUID): Future[Int] = repository.deleteById(id)

  private def executeIfTrue[T](predicate: => Boolean, action: => T, default: T): T = {
    if (predicate) action
    else default
  }
}
