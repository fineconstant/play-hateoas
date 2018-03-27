package controllers.v1

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import repository.dbao.EmployeesDBAO


@Singleton
class EmployeesController @Inject()(cc: ControllerComponents, repository: EmployeesDBAO)
  extends AbstractController(cc) {

  def employees: Action[AnyContent] = Action {
    Ok("/employees")
  }
}
