package common.hal

import play.api.libs.json.JsObject

case class HalLink(
  rel                 : String, href: String,
  deprecation         : Option[String] = None, name: Option[String] = None,
  profile             : Option[String] = None,
  title               : Option[String] = None, hreflang: Option[String] = None,
  `type`              : Option[String] = None,
  linkAttr            : JsObject = Defaults.emptyJson,
  templated           : Boolean = false
) {

  def withLinkAttributes(obj: JsObject): HalLink = this.copy(linkAttr = obj)

  def withDeprecation(url: String): HalLink = this.copy(deprecation = Some(url))

  def withName(name: String): HalLink = this.copy(name = Some(name))

  def withProfile(profile: String): HalLink = this.copy(profile = Some(profile))

  def withTitle(title: String): HalLink = this.copy(title = Some(title))

  def withHrefLang(lang: String): HalLink = this.copy(hreflang = Some(lang))

  def withType(mediaType: String): HalLink = this.copy(`type` = Some(mediaType))
}

// TODO: https://github.com/hmrc/play-hal