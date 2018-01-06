package database.provider.api

import akka.stream.alpakka.slick.javadsl.SlickSession

trait SlickDatabaseProvider {
  protected val sessionProvider: SlickSessionProvider
  val session: SlickSession = sessionProvider.session
  val db = session.db
  val profile = session.profile
}
