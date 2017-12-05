package base

import org.scalamock.scalatest.MockFactory
import org.scalatest._

trait DefaultFlatSpec
  extends FlatSpec
    with PrivateMethodTester
    with Matchers
    with MockFactory
    with OptionValues
    with EitherValues
    with GivenWhenThen
