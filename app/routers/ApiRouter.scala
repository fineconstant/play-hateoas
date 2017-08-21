package routers

import java.util.UUID
import javax.inject.Inject

import controllers.ApiController
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

import scala.language.implicitConversions

class ApiRouter @Inject()(controller: ApiController) extends SimpleRouter {

  import ApiRouter._

  override def routes: Routes = {
    case GET(p"/")           => controller.stream
    case GET(p"/employees/") => controller.employed
    case GET(p"/$id")        => controller.show(id)
    case POST(p"/")          => controller.process
    case DELETE(p"/$id")     => controller.delete(id)
    case PUT(p"/$id")        => controller.update(id)
    case PATCH(p"/$id")      => controller.update(id)
  }
}

object ApiRouter {
  implicit def string2UUID(x: String): UUID = UUID fromString x
}
