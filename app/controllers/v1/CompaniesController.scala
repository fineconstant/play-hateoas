package controllers.v1

import java.util.UUID
import javax.inject.{Inject, Singleton}

import common.validators.SanitizedUUID
import models.Company
import play.api.libs.json._
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

  def replaceWithNew: Action[JsValue] = Action(parse.json).async {
    implicit request =>
      val jsCompany = request.body.validate[Seq[Company]]

      jsCompany.fold[Future[Result]](
        errors => Future(BadRequest(Json.obj("message" -> JsError.toJson(errors)))),
        companies => {
          service.replaceWithNew(companies)
          .map {
            case Some(_) => Ok
            case _       => BadRequest
          }
        }
      )
  }

  // TODO: test for id != company.id
  def upsert(id: UUID): Action[JsValue] = Action(parse.json).async {
    implicit request =>
      val jsCompany = request.body.validate[Company]

      jsCompany.fold[Future[Result]](
        errors => Future(BadRequest(Json.obj("message" -> JsError.toJson(errors)))),
        company => {
          service.upsert(id,company)
          .map {
            case 1 => Ok
            case _ => BadRequest
          }
        }
      )
  }

  // TODO: test for id != company.id
  def update(id: UUID): Action[JsValue] = Action(parse.json).async {
    implicit request =>
      val jsCompany = request.body.validate[Company]

      jsCompany.fold[Future[Result]](
        errors => Future(BadRequest(Json.obj("message" -> JsError.toJson(errors)))),
        company => {
          service.update(id, company)
          .map {
            case 1 => Ok
            case _ => BadRequest
          }
        }
      )
  }

  def create: Action[JsValue] = Action(parse.json).async {
    implicit request =>
      val jsCompany = request.body.validate[Company]

      jsCompany.fold[Future[Result]](
        errors => Future(BadRequest(Json.obj("message" -> JsError.toJson(errors)))),
        company => {
          service.createCompany(company)
          .map {
            case 1 => Ok
            case _ => BadRequest
          }
        }
      )
  }

  def clear: Action[AnyContent] = Action.async {
    service.clear
    .map(deletedCount => Ok(deletedCount))
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
