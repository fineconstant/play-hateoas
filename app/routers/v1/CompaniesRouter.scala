package routers.v1

import java.util.UUID
import javax.inject.Inject

import controllers.v1.CompaniesController
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird.{PathBindableExtractor, _}

class CompaniesRouter @Inject()(controller: CompaniesController) extends SimpleRouter {
  implicit val uuid: PathBindableExtractor[UUID] = new PathBindableExtractor[UUID]

  override def routes: Routes = {
    case POST(p"/companies")               => controller.create
    case GET(p"/companies")                => controller.companies
    case GET(p"/companies/${uuid(id)}")    => controller.show(id)
    case DELETE(p"/companies/${uuid(id)}") => controller.delete(id)
  }
}