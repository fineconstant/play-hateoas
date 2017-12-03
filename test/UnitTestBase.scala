import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers, PrivateMethodTester}

trait UnitTestBase
  extends FlatSpec
    with PrivateMethodTester
    with GivenWhenThen
    with Matchers
    with MockitoSugar