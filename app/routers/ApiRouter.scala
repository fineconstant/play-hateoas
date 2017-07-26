package routers

import javax.inject.Inject

import controllers.ApiController
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class ApiRouter @Inject()(controller: ApiController) extends SimpleRouter {
  override def routes: Routes = {
    case GET(p"/")    => controller.index
    case POST(p"/")   => controller.process
    case GET(p"/$id") => controller.show(id)
  }
}
