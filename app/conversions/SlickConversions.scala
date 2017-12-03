package conversions

import java.sql.Timestamp
import java.time.LocalDate

import play.Logger
import slick.ast.BaseTypedType
import slick.jdbc.PostgresProfile.api._
import slick.jdbc.JdbcType

object SlickConversions {

  /**
    * Slick does not know how to convert LocalDate, we must implement that conversion
    */
  implicit val localDateMapper: JdbcType[LocalDate] with BaseTypedType[LocalDate] =
    MappedColumnType.base[LocalDate, Timestamp](
      (localDate: LocalDate) => {
        Logger.debug(s"Converting from LocalDate: [$localDate]")
        val result = Timestamp valueOf localDate.atStartOfDay
        Logger.debug(s"LocalDate converted to Timestamp: [$result]")
        result
      },
      (timestamp: Timestamp) => {
        Logger.debug(s"Converting from Timestamp: [$timestamp]")
        val result = timestamp.toLocalDateTime.toLocalDate
        Logger.debug(s"Timestamp converted to LocalDate: [$result]")
        result
      }
    )
}
