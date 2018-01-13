package database.provider

import javax.inject.{Inject, Singleton}

import database.provider.api.{ApplicationDatabaseProvider, SlickSessionProvider}

@Singleton
class SlickDatabaseProvider @Inject()(val sessionProvider: SlickSessionProvider) extends ApplicationDatabaseProvider
