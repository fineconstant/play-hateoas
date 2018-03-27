package database.provider

import database.provider.api.{ApplicationDatabaseProvider, SlickSessionProvider}
import javax.inject.{Inject, Singleton}


@Singleton
class SlickDatabaseProvider @Inject()(val sessionProvider: SlickSessionProvider) extends ApplicationDatabaseProvider
