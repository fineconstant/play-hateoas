package repository

import java.util.UUID
import javax.inject.{Inject, Singleton}

import akka.NotUsed
import akka.stream.scaladsl.Source
import models.Company
import repository.dbao.CompaniesDBAO

import scala.concurrent.Future

@Singleton
class CompaniesRepository @Inject()(dbao: CompaniesDBAO) {

  def listAll: Source[Company, NotUsed] = dbao.stream

  def findById(id: UUID): Future[Option[Company]] = dbao.findById(id)

  def deleteById(id: UUID): Future[Int] = dbao.delete(id)

}
