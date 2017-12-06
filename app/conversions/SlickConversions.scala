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
    Logger.debug(s"Converting from LocalDate: [$localDate]")

    val result = Option(localDate)
      .map(_.atStartOfDay)
      .map(Timestamp.valueOf)
      .getOrElse {
        val x = new Timestamp(0L)
        Logger.error(s"timestamp can not be null, providing with the default [$x]")
        x
      }

    Logger.debug(s"LocalDate converted to Timestamp: [$result]")
    result
  }

  private def timestamp2LocalDate(timestamp: Timestamp) = {
    Logger.debug(s"Converting from Timestamp: [$timestamp]")

    val result = Option(timestamp)
      .map(_.toLocalDateTime)
      .map(_.toLocalDate)
      .getOrElse {
        val x = LocalDate.MIN
        Logger.error(s"localDate can not be null, providing with the default [$x]")
        x
      }

    Logger.debug(s"Timestamp converted to LocalDate: [$result]")
    result
  }

}
