package controllers

import javax.inject.{Inject, Singleton}

import models.Person
import play.api.Logger
import play.api.libs.json.JsValue
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

@Singleton
class ApiController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def person: Action[AnyContent] = Action { implicit request => Ok("TEST") }

  private def parseJson(json: JsValue) = Action { implicit request =>
    //val person = json.as[Person]
    //Logger.trace(s"Parsed: $person")
    //Ok(person)
    ???
  }
}
