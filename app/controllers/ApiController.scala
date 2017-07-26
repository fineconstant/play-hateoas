package controllers

import javax.inject.{Inject, Singleton}

import play.api.libs.json.JsValue
import play.api.mvc._

@Singleton
class ApiController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def index: Action[AnyContent] = {
    Action { implicit request =>
      val r: Result = Ok("hello world")
      r
    }
  }

  def process: Nothing = ???

  def show(id: String): Nothing = ???

  private def parseJson(json: JsValue) = Action { implicit request =>
    //val person = json.as[Person]
    //Logger.trace(s"Parsed: $person")
    //Ok(person)
    ???
  }
}
