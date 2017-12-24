package base

import org.scalamock.scalatest.MockFactory
import org.scalatest._
import org.scalatestplus.play.WsScalaTestClient

trait BaseFlatSpec
    extends FlatSpecLike
    with PrivateMethodTester
    with Matchers
    with MockFactory
    with OptionValues
    with EitherValues
    with GivenWhenThen
    with BeforeAndAfter
    with BeforeAndAfterAll
    with WsScalaTestClient