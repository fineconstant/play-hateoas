package controllers.v1

import javax.inject.{Inject, Singleton}

import play.api.mvc.{AbstractController, ControllerComponents}
import repository.EmployeesRepository

@Singleton
class EmployeesController @Inject()(cc: ControllerComponents, repository: EmployeesRepository)
  extends AbstractController(cc) {

  def employees = Action {
    Ok("/employees")
  }
}
