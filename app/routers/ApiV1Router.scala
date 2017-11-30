package routers

import java.util.UUID
import javax.inject.Inject

import controllers.ApiController
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class ApiV1Router @Inject()(controller: ApiController) extends SimpleRouter {

  implicit val uuid: PathBindableExtractor[UUID] = new PathBindableExtractor[UUID]

  override def routes: Routes = {
    case GET(p"/")                => controller.stream
    case GET(p"/employees/tuple") => controller.employedTuple
    case GET(p"/employees/case")  => controller.employedTuple
    case GET(p"/${uuid(id)}")     => controller.show(id)
    case POST(p"/")               => controller.process
    case DELETE(p"/${uuid(id)}")  => controller.delete(id)
    case PUT(p"/${uuid(id)}")     => controller.update(id)
    case PATCH(p"/${uuid(id)}")   => controller.update(id)
  }
}
