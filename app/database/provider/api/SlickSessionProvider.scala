package database.provider.api

import akka.stream.alpakka.slick.javadsl.SlickSession


trait SlickSessionProvider {
  val session: SlickSession
}
