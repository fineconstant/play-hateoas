package common.hal

import play.api.libs.json.JsObject

case class HalResource(
  links: HalLinks,
  state: JsObject,
  embedded: Vector[(String, Vector[HalResource])] = Vector.empty
) {

  def ++(other: HalResource): HalResource =
    HalResource(links ++ other.links, state ++ other.state, embedded ++ other.embedded)

  def include(other: HalResource): HalResource = ++(other)

  def ++(link: HalLink): HalResource = {
    this.copy(links = links ++ link)
  }

  def include(link: HalLink): HalResource = ++(link)
}
