package routers

import javax.inject.Inject

import controllers.ApiController
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class ApiRouter @Inject()(controller: ApiController) extends SimpleRouter {
  override def routes: Routes = {
    case GET(p"/")       => controller.index
    case GET(p"/$id")    => controller.show(id)
    case POST(p"/")      => controller.process
    case DELETE(p"/$id") => controller.delete(id)
    case PUT(p"/$id")    => controller.update(id)
    case PATCH(p"/$id")  => controller.update(id)
  }
}
