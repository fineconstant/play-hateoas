package database.context

import akka.actor.ActorSystem
import javax.inject.{Inject, Singleton}
import play.api.libs.concurrent.CustomExecutionContext


/**
  * This class is a pointer to an execution context configured to point to "database.dispatcher"
  * in the "application.conf" file.
  */
@Singleton
class DatabaseExecutionContext @Inject()(system: ActorSystem)
  extends CustomExecutionContext(system, "database.dispatcher")
