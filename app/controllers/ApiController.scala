package controllers

import java.time.LocalDate
import java.util.UUID
import javax.inject.{Inject, Singleton}

import conversions.JsonConversions.Person._
import models.Person
import play.api.libs.json._
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ApiController @Inject()(cc: ControllerComponents)(implicit ec: ExecutionContext)
  extends AbstractController(cc) {

  def index: Action[AnyContent] = {
    Action.async {
      val json = Json.toJson(
        Seq(Person(UUID.randomUUID(), "Harry", "Potter", LocalDate.now(), UUID.randomUUID),
            Person(UUID.randomUUID(), "Ron", "Wesley", LocalDate.now(), UUID.randomUUID)))
      Future.successful(Ok(json))
    }
  }

  def show(id: UUID): Action[AnyContent] = {
    Action.async {
      implicit request =>
        val json = Json.toJson(Person(UUID.randomUUID, "Harry", "Potter", LocalDate.now(), UUID.randomUUID))
        Future.successful(Ok(json))
    }
  }

  def process: Action[JsValue] = Action(parse.json) { implicit request =>
    request.body.validate[Person]
      .fold(
        errors =>
          BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toJson(errors))),
        restResource => {
          println(restResource)
          Ok(Json.toJson(restResource))
        }
      )
  }

  def delete(id: String): Action[AnyContent] = {???}

  def update(id: String): Action[AnyContent] = {???}

}
