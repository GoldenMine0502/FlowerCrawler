package kr.goldenmine.siteinfo

import org.openqa.selenium.WebElement

interface ISkippableSiteInfo : ISiteInfo {
    val skipSites: List<String>

    fun hasSkippableLinks(divElement: WebElement): Boolean
}