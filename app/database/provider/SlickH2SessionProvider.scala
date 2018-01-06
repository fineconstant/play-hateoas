package database.provider

import javax.inject.Singleton

import akka.stream.alpakka.slick.javadsl.SlickSession
import database.provider.api.SlickSessionProvider

@Singleton
final class SlickH2SessionProvider extends SlickSessionProvider {
  val session: SlickSession = SlickSession.forConfig("slick.dbs.h2")
}
