package conversions

import java.sql.Timestamp
import java.time.{LocalDate, LocalDateTime}

import base.DefaultFlatSpec

//noinspection TypeAnnotation
class SlickConversionsSpec extends DefaultFlatSpec {

  trait Fixture {
    val underTest = SlickConversions
  }

  behavior of "localDateMapper"

  it should "have sql type of TIMESTAMP" in new Fixture {
    val actual = underTest.localDateMapper sqlTypeName None

    println(underTest.localDateMapper.scalaType.toString)

    val expected = "TIMESTAMP"

    actual shouldBe expected
  }

  it should "have scala type of java.time.LocalDate" in new Fixture {
    val actual = underTest.localDateMapper.scalaType.toString

    val expected = "java.time.LocalDate"

    actual shouldBe expected
  }


  behavior of "localDate2Timestamp"

  it should "throw an IllegalArgumentException when null parameter passed" in new Fixture {
    val localDate2Timestamp = PrivateMethod[Timestamp]('localDate2Timestamp)
    val date = null

    the[IllegalArgumentException] thrownBy {
      underTest invokePrivate localDate2Timestamp(date)
    } should have message "requirement failed: localDate must not be null"
  }
  it should "convert minimal date to timestamp" in new Fixture {
    val localDate2Timestamp = PrivateMethod[Timestamp]('localDate2Timestamp)
    val date = LocalDate.MIN

    val actual = underTest invokePrivate localDate2Timestamp(date)
    val expected = Timestamp.valueOf(date.atStartOfDay)

    actual shouldBe expected
  }

  it should "convert maximal date to timestamp" in new Fixture {
    val localDate2Timestamp = PrivateMethod[Timestamp]('localDate2Timestamp)
    val date = LocalDate.MAX

    val actual = underTest invokePrivate localDate2Timestamp(date)
    val expected = Timestamp.valueOf(date.atStartOfDay)

    actual shouldBe expected
  }

  it should "convert realistic date to timestamp" in new Fixture {
    val localDate2Timestamp = PrivateMethod[Timestamp]('localDate2Timestamp)
    val date = LocalDate.of(2000, 1, 1)

    val actual = underTest invokePrivate localDate2Timestamp(date)
    val expected = Timestamp.valueOf(date.atStartOfDay)

    actual shouldBe expected
  }


  behavior of "timestamp2LocalDate"

  it should "throw an IllegalArgumentException when null timestamp passed" in new Fixture {
    val timestamp2LocalDate = PrivateMethod[LocalDate]('timestamp2LocalDate)
    val timestamp = null

    the[IllegalArgumentException] thrownBy {
      underTest invokePrivate timestamp2LocalDate(timestamp)
    } should have message "requirement failed: timestamp must not be null"
  }

  it should "convert minimal timestamp to date" in new Fixture {
    val timestamp2LocalDate = PrivateMethod[LocalDate]('timestamp2LocalDate)
    val timestamp = Timestamp.valueOf(LocalDateTime.MIN)

    val actual = underTest invokePrivate timestamp2LocalDate(timestamp)
    val expected = timestamp.toLocalDateTime.toLocalDate

    actual shouldBe expected
  }

  it should "convert maximal timestamp to date" in new Fixture {
    val timestamp2LocalDate = PrivateMethod[LocalDate]('timestamp2LocalDate)
    val timestamp = Timestamp.valueOf(LocalDateTime.MAX)

    val actual = underTest invokePrivate timestamp2LocalDate(timestamp)
    val expected = timestamp.toLocalDateTime.toLocalDate

    actual shouldBe expected
  }

  it should "convert realistic timestamp to date" in new Fixture {
    val timestamp2LocalDate = PrivateMethod[LocalDate]('timestamp2LocalDate)
    val timestamp = Timestamp.valueOf(LocalDate.of(2000, 1, 1).atStartOfDay())

    val actual = underTest invokePrivate timestamp2LocalDate(timestamp)
    val expected = timestamp.toLocalDateTime.toLocalDate

    actual shouldBe expected
  }

}
