package controllers.v1

import javax.inject.{Inject, Singleton}

import akka.stream.scaladsl.Source
import models.Employee
import play.api.libs.json._
import play.api.mvc._
import repository.dbao.{CompaniesDBAO, EmployeesDBAO}

import scala.concurrent.{ExecutionContext, Future}

// TODO: split into companies and employees controllers
@Singleton
class ApiController @Inject()(companies: CompaniesDBAO, employees: EmployeesDBAO, cc: ControllerComponents)
  (implicit ec: ExecutionContext) extends AbstractController(cc) {

  def employedTuple: Action[AnyContent] = Action.async {
    Future.successful(Ok.chunked(Source.fromPublisher(employees.employedTuple).map(c => Json.toJson(c))))
  }

  def employedCaseClass: Action[AnyContent] = Action.async {
    Future.successful(Ok.chunked(Source.fromPublisher(employees.employedCaseClass).map(c => Json.toJson(c))))
  }



}
