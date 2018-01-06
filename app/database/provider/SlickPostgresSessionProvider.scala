package database.provider

import javax.inject.Singleton

import akka.stream.alpakka.slick.javadsl.SlickSession
import database.provider.api.SlickSessionProvider

@Singleton
final class SlickPostgresSessionProvider extends SlickSessionProvider {
  override val session: SlickSession = SlickSession.forConfig("slick.dbs.postgres")
}

