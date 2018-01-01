package controllers.v1

import java.util.UUID
import javax.inject.{Inject, Singleton}

import common.validators.SanitizedUUID
import play.api.libs.json.Json
import play.api.mvc._
import services.CompaniesService

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CompaniesController @Inject()(cc: ControllerComponents, service: CompaniesService)
  (implicit ec: ExecutionContext) extends AbstractController(cc) {

  def companies: Action[AnyContent] = Action.async {
    Future.successful(Ok.chunked(service.companiesJson))
  }

  def show(id: UUID): Action[AnyContent] = {
    val sanitizedId = SanitizedUUID(id)

    Action.async {
      service.findById(sanitizedId)
      .map {
        case Some(company) => Ok(Json.toJson(company))
        case None          => NotFound
      }
    }
  }

  def delete(id: UUID): Action[AnyContent] = {
    val sanitizedUUID = SanitizedUUID(id)

    Action.async {
      service.deleteById(sanitizedUUID)
      .map {
        case x: Int if x > 0 => Ok(Json.toJson(1))
        case 0               => NotFound
      }
    }
  }
}
