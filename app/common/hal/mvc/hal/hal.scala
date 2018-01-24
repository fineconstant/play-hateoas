package common.hal.mvc

import common.hal.HalResource
import play.api.http.Writeable
import play.api.libs.json.Json
import play.api.mvc.{Accepting, Codec}

package object hal {

  val halMimeType = "application/hal+json"

  val AcceptHal = Accepting(halMimeType)

  implicit def halWriter(implicit code: Codec): Writeable[HalResource] =
    Writeable(d => code.encode(Json.toJson(d).toString()), Some(halMimeType))
}
