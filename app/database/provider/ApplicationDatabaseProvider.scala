package database.provider

import javax.inject.{Inject, Singleton}

import database.provider.api.{SlickDatabaseProvider, SlickSessionProvider}

@Singleton
class ApplicationDatabaseProvider @Inject()(val sessionProvider: SlickSessionProvider) extends SlickDatabaseProvider
