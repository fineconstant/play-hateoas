package routers.v1

import java.util.UUID

import controllers.v1.CompaniesController
import javax.inject.Inject
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird.{PathBindableExtractor, _}


class CompaniesRouter @Inject()(controller: CompaniesController) extends SimpleRouter {
  implicit val uuid: PathBindableExtractor[UUID] = new PathBindableExtractor[UUID]

  override def routes: Routes = {
    case GET(p"/companies") => controller.companies
    case GET(p"/companies/${uuid(id)}") => controller.show(id)
    case PUT(p"/companies") => controller.replaceWithNew
    case PUT(p"/companies/${uuid(id)}") => controller.upsert(id)
    case PATCH(p"/companies/${uuid(id)}") => controller.update(id)
    case POST(p"/companies") => controller.create
    case DELETE(p"/companies") => controller.clear
    case DELETE(p"/companies/${uuid(id)}") => controller.delete(id)
  }
}
