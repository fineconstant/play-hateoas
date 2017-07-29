package controllers

import java.util.UUID
import javax.inject.{Inject, Singleton}

import models.RestResource
import play.api.libs.json.{JsError, JsValue, Json}
import play.api.mvc._

import scala.concurrent.Future

@Singleton
class ApiController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index: Action[AnyContent] = {
    val resources = Seq(RestResource(UUID.randomUUID().toString, "name 1", "link 2"),
                        RestResource(UUID.randomUUID().toString, "name 2", "link 2"))
    Action.async {
      val json = Json.toJson(resources)
      Future.successful(Ok(json))
    }
  }

  def show(id: String): Action[AnyContent] = {
    Action.async {
      implicit request =>
        val json = Json.toJson(RestResource(UUID.randomUUID().toString, "name x", "link x"))
        Future.successful(Ok(json))
    }
  }

  def process: Action[JsValue] = Action(parse.json) { implicit request =>
    request.body.validate[RestResource]
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
