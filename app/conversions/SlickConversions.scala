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
    MappedColumnType.base[LocalDate, Timestamp](localDate2Timestamp, timestamp2LocalDate)

  private def localDate2Timestamp(localDate: LocalDate) = {
    require(localDate!=null, "localDate must not be null")

    Logger.debug(s"Converting from LocalDate: [$localDate]")
    val result = Timestamp valueOf localDate.atStartOfDay
    Logger.debug(s"LocalDate converted to Timestamp: [$result]")
    result
  }

  private def timestamp2LocalDate(timestamp: Timestamp) = {
    require(timestamp!=null, "timestamp must not be null")

    Logger.debug(s"Converting from Timestamp: [$timestamp]")
    val result = timestamp.toLocalDateTime.toLocalDate
    Logger.debug(s"Timestamp converted to LocalDate: [$result]")
    result
  }

}
