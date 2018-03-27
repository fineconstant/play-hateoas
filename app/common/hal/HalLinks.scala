package common.hal

object HalLinks {
  def empty = HalLinks(Vector.empty)
}

case class HalLinks(links: Vector[HalLink]) {
  def include(other: HalLinks): HalLinks = ++(other)

  def ++(other: HalLinks): HalLinks = {
    HalLinks(links ++ other.links)
  }

  def include(link: HalLink): HalLinks = ++(link)

  def ++(link: HalLink) = HalLinks(link +: this.links)
}
