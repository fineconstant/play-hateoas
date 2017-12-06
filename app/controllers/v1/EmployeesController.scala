package controllers.v1

import javax.inject.{Inject, Singleton}

import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import repository.EmployeesRepository

@Singleton
class EmployeesController @Inject()(cc: ControllerComponents, repository: EmployeesRepository)
  extends AbstractController(cc) {

  def employees: Action[AnyContent] = Action {
    Ok("/employees")
  }
}
