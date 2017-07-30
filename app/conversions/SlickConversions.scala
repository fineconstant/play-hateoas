package conversions

import java.sql.Timestamp
import java.time.LocalDate

import slick.ast.BaseTypedType
import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcType

object SlickConversions {

  /**
    * Slick does not know how to convert [[LocalDate]], we must implement that conversion
    */
  implicit val localDateMapper: JdbcType[LocalDate] with BaseTypedType[LocalDate] = MappedColumnType
    .base[LocalDate, Timestamp](
    (localDate: LocalDate) => Timestamp valueOf localDate.toString,
    (timestamp: Timestamp) => timestamp.toLocalDateTime.toLocalDate
  )
}
