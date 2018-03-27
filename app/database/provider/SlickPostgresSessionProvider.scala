package database.provider

import akka.stream.alpakka.slick.javadsl.SlickSession
import database.provider.api.SlickSessionProvider
import javax.inject.Singleton


@Singleton
final class SlickPostgresSessionProvider extends SlickSessionProvider {
  override val session: SlickSession = SlickSession.forConfig("slick.dbs.postgres")
}

