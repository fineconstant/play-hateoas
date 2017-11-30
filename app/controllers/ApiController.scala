package controllers

import java.time.LocalDate
import java.util.UUID
import javax.inject.{Inject, Singleton}

import akka.stream.scaladsl.Source
import models.Employee
import play.api.Logger
import play.api.libs.json._
import play.api.mvc._
import repository.{CompaniesRepository, EmployeesRepository}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ApiController @Inject()(companies: CompaniesRepository, employees: EmployeesRepository, cc: ControllerComponents)
  (implicit ec: ExecutionContext) extends AbstractController(cc) {

  def stream: Action[AnyContent] = {
    Action.async {
      //Future.successful(Ok.chunked(Source.fromPublisher(companies.stream).map(c => Json.toJson(c))))


      Future.successful(Ok("OK"))
    }
  }

  def show(id: UUID): Action[AnyContent] = {
    val sanitizedId = UUID fromString id.toString
    Logger.info(s"Sanitized UUID: [$sanitizedId]")
    Action.async {
      implicit request =>
        //val json = Json.toJson(Employee(UUID.randomUUID, "Harry", "Potter", LocalDate.now(), UUID.randomUUID))
        Future.successful(Ok(id.toString))
    }
  }

  def employedTuple: Action[AnyContent] = {
    Action.async {
      Future.successful(Ok.chunked(Source.fromPublisher(employees.employedTuple).map(c => Json.toJson(c))))
    }
  }

  def employedCaseClass: Action[AnyContent] = {
    Action.async {
      Future.successful(Ok.chunked(Source.fromPublisher(employees.employedCaseClass).map(c => Json.toJson(c))))
    }
  }

  def process: Action[JsValue] = Action(parse.json) { implicit request =>
    request.body.validate[Employee]
      .fold(
        errors =>
          BadRequest(Json.obj("status" -> "OK", "message" -> JsError.toJson(errors))),
        restResource => {
          println(restResource)
          Ok(Json.toJson(restResource))
        }
      )
  }

  def delete(id: UUID): Action[AnyContent] = {???}

  def update(id: UUID): Action[AnyContent] = {???}

}
