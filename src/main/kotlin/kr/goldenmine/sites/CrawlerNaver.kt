package kr.goldenmine.sites

import kr.goldenmine.findElementsWhileNotEmpty
import kr.goldenmine.getDefaultDimension
import kr.goldenmine.setChromePath
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import java.io.*
import java.lang.Thread.sleep
import java.util.*

class CrawlerNaver(
    override val folder: File,
    override val searchImgPer: Int,
    override val name: String
) : ICrawlingSite {

    private val driver: WebDriver

    init {
        setChromePath("lib/chromedriver.exe")

        driver = ChromeDriver()
        driver.manage().window().size = getDefaultDimension()

        Runtime.getRuntime().addShutdownHook(Thread {
            driver.quit()
        })
    }

    override fun useKeyword(kor: String, eng22: String?, eng16: String?): String = "$kor"

    override fun getDriver(): WebDriver = driver

    override fun getSearchLink(keyword: String) = "https://search.naver.com/search.naver?where=image&query=$keyword"
    override fun getImgElements(): List<WebElement> {

        return driver
            .findElementsWhileNotEmpty(By.className("photo_tile"))[0]
            .findElements(By.className("tile_item"))
            .filter { !(it.text ?: "").contains("추천 키워드") }
            .map { it.findElement(By.tagName("img")) }
    }

    override fun pastDoing() {
        sleep(100)
        repeat(5) {
            scroll(150, 250)
            sleep(50)
        }
    }

    private fun scroll(minimumPx: Int, maximumPx: Int) {
        val jsExecutor = driver as JavascriptExecutor
        jsExecutor.executeScript("window.scrollBy(0, ${getRandom(minimumPx, maximumPx)})")
    }
}

private val r: Random = Random()

fun getRandom(start: Int, finish: Int): Int {
    return r.nextInt(finish - start + 1) + start
}