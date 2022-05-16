package kr.goldenmine.siteinfo.naver

import kr.goldenmine.util.findElementsWhileNotEmpty
import kr.goldenmine.siteinfo.FlowerInfo
import kr.goldenmine.siteinfo.ISkippableSiteInfo
import kr.goldenmine.primitives.sites.getRandom
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class SiteInfoNaver(override val skipSites: List<String>) : ISkippableSiteInfo {
    override fun hasSkippableLinks(divElement: WebElement): Boolean {
        val src = divElement.getAttribute("src")

        return skipSites.any { text ->
            return src.contains(text) || text.contains(src)
        }
    }

    override val name: String
        get() = "naver_kor"

    override fun getSearchKeyword(flowerInfo: FlowerInfo): String = flowerInfo.korean

    override fun getSearchLink(keyword: String) = "https://search.naver.com/search.naver?where=image&query=$keyword"

    override fun getImgElements(driver: WebDriver): List<WebElement> {
        return driver
            .findElementsWhileNotEmpty(By.className("photo_tile"))[0]
            .findElements(By.className("tile_item"))
            .filter { !(it.text ?: "").contains("추천 키워드") }
            .map { it.findElement(By.tagName("img")) }
            .filter { !hasSkippableLinks(it) }
    }

    override fun doAfterGetLink(driver: WebDriver) {
        Thread.sleep(100)
        repeat(5) {
            scroll(driver, 150, 250)
            Thread.sleep(50)
        }
    }

    private fun scroll(driver: WebDriver, minimumPx: Int, maximumPx: Int) {
        val jsExecutor = driver as JavascriptExecutor
        jsExecutor.executeScript("window.scrollBy(0, ${getRandom(minimumPx, maximumPx)})")
    }
}
