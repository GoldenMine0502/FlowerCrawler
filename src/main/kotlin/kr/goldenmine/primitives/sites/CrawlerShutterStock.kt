package kr.goldenmine.primitives.sites

import kr.goldenmine.findElementsWhileNotEmpty
import kr.goldenmine.getDefaultDimension
import kr.goldenmine.setChromePath
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import java.io.*

class CrawlerShutterStock(
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

    override fun useKeyword(kor: String, eng22: String?, eng16: String?): String = "$eng16"

    override fun getDriver(): WebDriver = driver

    override fun getSearchLink(keyword: String) = "https://www.shutterstock.com/ko/search/$keyword"

    //https://stackoverflow.com/questions/21713280/find-div-element-by-multiple-class-names

    override fun getImgElements(): List<WebElement> {
        return driver
//            .findElementsWhileNotEmpty(By.className("jss198"))[0]
            .findElementsWhileNotEmpty(By.tagName("img"))
            .asSequence()
            .filter { "shutterstock" != it.getAttribute("alt") }
            .drop(2)
            .toList()
    }

    override fun pastDoing() {

    }

}