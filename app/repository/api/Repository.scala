package repository.api

import org.reactivestreams.Publisher

import scala.concurrent.Future

trait Repository[T] extends DBComponent {
  def stream: Publisher[T]

  def save(x: T): Future[Int]

  def save(xs: Seq[T]): Future[AnyRef]

  def drop: Future[Int]

  def createSchemaIfNotExists(): Future[Unit]

  def dropTableIfExists(): Future[Unit]
}
