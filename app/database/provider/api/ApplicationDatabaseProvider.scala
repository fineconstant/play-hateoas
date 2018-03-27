package database.provider.api

import akka.stream.alpakka.slick.javadsl.SlickSession


trait ApplicationDatabaseProvider {
  val session: SlickSession = sessionProvider.session
  val db = session.db
  val profile = session.profile
  protected val sessionProvider: SlickSessionProvider
}
