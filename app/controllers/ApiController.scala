package controllers

import java.util.UUID
import javax.inject.{Inject, Singleton}

import models.RestResource
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._

@Singleton
class ApiController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index: Action[AnyContent] = {
    val resources = Seq(RestResource(UUID.randomUUID(), "name 1", "link 2"),
                        RestResource(UUID.randomUUID(), "name 2", "link 2"))
    Action {
      Ok(Json.toJson(resources))
    }
  }

  def show(id: String) = ???

  def process = ???

  def delete(id: String) = ???

  def update(id: String) = ???

  private def parseJson(json: JsValue) = Action { implicit request =>
    //val person = json.as[Person]
    //Logger.trace(s"Parsed: $person")
    //Ok(person)
    ???
  }
}
