package kr.goldenmine.siteinfo.google

import kr.goldenmine.findElementsWhileNotEmpty
import kr.goldenmine.siteinfo.ISkippableSiteInfo
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

abstract class SiteInfoGoogle(override val skipSites: List<String>) : ISkippableSiteInfo {

    override fun hasSkippableLinks(divElement: WebElement): Boolean {
//        val tagText = divElement.findElement(By.className("fxgdke")).text
        // VFACy kGQAp sMi44c lNHeqe WGvvNb
        // VFACy를 포함하는 모든 태그는 위 4개 태그를 추가로 다 갖고 있었다.
        val tagText = divElement.findElement(By.className("VFACy")).text

        return skipSites.any { text ->
            return tagText.contains(text) || text.contains(tagText)
        }
    }

    override fun getImgElements(driver: WebDriver): List<WebElement> {
        // isv-r PNCib MSM1fd BUooTd div
        // ipukc = 연관검색어 관련 클래스
        // MSM1fd = 그냥 클래스
        return driver
            .findElementsWhileNotEmpty(By.className("MSM1fd"))
            .filter { !hasSkippableLinks(it) }
            .map { it.findElement(By.className("Q4LuWd")) }
    }

    override fun getSearchLink(keyword: String) = "https://www.google.com/search?q=${keyword}&source=lnms&tbm=isch"

    override fun doAfterGetLink(driver: WebDriver) {

    }
}