package kr.goldenmine.siteinfo

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

interface ISiteInfo {
    val name: String

    fun getSearchKeyword(flowerInfo: FlowerInfo): String

    fun getImgElements(driver: WebDriver): List<WebElement>

    fun getSearchLink(keyword: String): String

    fun doAfterGetLink(driver: WebDriver)
}