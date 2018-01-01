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

  def deleteById(id: UUID): Future[Int] = repository.deleteById(id)

}
