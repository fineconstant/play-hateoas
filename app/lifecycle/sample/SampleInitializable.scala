package lifecycle.sample

import lifecycle.Initializable
import play.api.Logger

class SampleInitializable extends Initializable {
  val log = Logger(classOf[SampleInitializable])
  log.info(s"Sample Initializable")
}
