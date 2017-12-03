package controllers.v1

import javax.inject.{Inject, Singleton}

import play.api.mvc.{AbstractController, ControllerComponents}
import repository.CompaniesRepository

import scala.concurrent.ExecutionContext

@Singleton
class CompaniesController @Inject()(cc: ControllerComponents, repository: CompaniesRepository)
  (implicit ec: ExecutionContext) extends AbstractController(cc) {

  def companies = Action {
    Ok("/companies")
  }
}
