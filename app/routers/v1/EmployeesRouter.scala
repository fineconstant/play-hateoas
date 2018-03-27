package routers.v1

import java.util.UUID

import com.google.inject.Inject
import controllers.v1.EmployeesController
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._


class EmployeesRouter @Inject()(controller: EmployeesController) extends SimpleRouter {
  implicit val uuid: PathBindableExtractor[UUID] = new PathBindableExtractor[UUID]

  override def routes: Routes = {
    case GET(p"/employees") => controller.employees
  }
}
