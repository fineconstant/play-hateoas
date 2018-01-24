package common.hal

object HalLinks {
  def empty = HalLinks(Vector.empty)
}

case class HalLinks(links: Vector[HalLink]) {
  def ++(other: HalLinks): HalLinks = {
    HalLinks(links ++ other.links)
  }

  def include(other: HalLinks): HalLinks = ++(other)

  def ++(link: HalLink) = HalLinks(link +: this.links)

  def include(link: HalLink): HalLinks = ++(link)
}
