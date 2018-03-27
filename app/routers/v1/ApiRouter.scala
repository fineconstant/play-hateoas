package routers.v1

import javax.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter


class ApiRouter @Inject()(companiesRouter: CompaniesRouter, employeesRouter: EmployeesRouter)
  extends SimpleRouter {

  override def routes: Routes =
    companiesRouter.routes
      .orElse(employeesRouter.routes)
}
