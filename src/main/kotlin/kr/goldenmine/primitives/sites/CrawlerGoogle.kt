package kr.goldenmine.primitives.sites

import kr.goldenmine.util.findElementsWhileNotEmpty
import kr.goldenmine.util.getDefaultDimension
import kr.goldenmine.util.setChromePath
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import java.io.*

class CrawlerGoogle(
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

    override fun useKeyword(kor: String, eng22: String?, eng16: String?): String = "$eng22"

    override fun getDriver(): WebDriver = driver

    override fun getSearchLink(keyword: String) = "https://www.google.com/search?q=${keyword}&source=lnms&tbm=isch"

    override fun getImgElements(): List<WebElement> {
        return driver
            .findElementsWhileNotEmpty(By.className("Q4LuWd"))
//            .findElementsWhileNotEmpty(By.className("mJxzWe"))[0]
//            .findElementsWhileNotEmpty(By.tagName("img"))
    }

    override fun pastDoing() {

    }

}