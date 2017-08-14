package conversions

import java.sql.Timestamp
import java.time.LocalDate

import play.Logger
import slick.ast.BaseTypedType
import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcType

object SlickConversions {

  /**
    * Slick does not know how to convert [[LocalDate]], we must implement that conversion
    */
  implicit val localDateMapper: JdbcType[LocalDate] with BaseTypedType[LocalDate] = MappedColumnType
    .base[LocalDate, Timestamp](
    (localDate: LocalDate) => {
      val result = Timestamp valueOf localDate.toString
      Logger.debug(s"Converting [$localDate] from LocalDate into Timestamp: [$result]")
      result
    },
    (timestamp: Timestamp) => {
      val result = timestamp.toLocalDateTime.toLocalDate
      Logger.debug(s"Converting [$timestamp] from Timestamp into LocalDate: [$result]")
      result
    }
  )
}
