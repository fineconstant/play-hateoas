package controllers

import java.time.{LocalDate, LocalDateTime}
import java.util.UUID
import javax.inject.{Inject, Singleton}

import models.Person
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.json._
import play.api.mvc._
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}
import conversions.JsonConversions.Person._
import play.api.db.NamedDatabase

@Singleton
class ApiController @Inject()
(cc: ControllerComponents, @NamedDatabase("h2mem") val dbConfigProvider: DatabaseConfigProvider)
  (implicit ec: ExecutionContext)
  extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {

  def index: Action[AnyContent] = {
    Action.async {
      val json = Json.toJson(
        Seq(Person(UUID.randomUUID(), "Harry", "Potter", LocalDate.now(), UUID.randomUUID()),
            Person(UUID.randomUUID(), "Ron", "Wesley", LocalDate.now(), UUID.randomUUID())))
      Future.successful(Ok(json))
    }
  }

  def show(id: UUID): Action[AnyContent] = {
    Action.async {
      implicit request =>
        val json = Json.toJson(Person(UUID.randomUUID(), "Harry", "Potter", LocalDate.now(), UUID.randomUUID()))
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

  def delete(id: String) = ???

  def update(id: String) = ???

}
