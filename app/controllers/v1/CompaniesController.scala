package controllers.v1

import java.util.UUID
import javax.inject.{Inject, Singleton}

import play.api.Logger
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.CompaniesService

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CompaniesController @Inject()(cc: ControllerComponents, service: CompaniesService)
  (implicit ec: ExecutionContext) extends AbstractController(cc) {

  def companies: Action[AnyContent] = Action.async {
    Future.successful(Ok.chunked(service.companiesJson))
  }

  def show(id: UUID): Action[AnyContent] = {
    val sanitizedId = UUID fromString id.toString
    Logger.info(s"Sanitized UUID: [$sanitizedId]")

    Action.async {
      service.findById(id)
      .map {
        case Some(company) => Ok(Json.toJson(company))
        case None          => NotFound
      }
    }
  }
}
