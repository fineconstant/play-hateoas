package base

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.testkit.TestKit
import com.typesafe.config.ConfigFactory

 class AkkaTestKit
  extends TestKit(ActorSystem("TestingActorSystem",
    ConfigFactory.parseString("akka.stream.materializer.debug.fuzzing-mode = on"))) {
  this: BaseFlatSpec =>

  implicit val actorSystem: ActorSystem = system
  implicit val materializer: ActorMaterializer = ActorMaterializer()

}
