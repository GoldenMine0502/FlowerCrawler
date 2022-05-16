package kr.goldenmine.siteinfo.shutterstock

import kr.goldenmine.findElementsWhileNotEmpty
import kr.goldenmine.siteinfo.FlowerInfo
import kr.goldenmine.siteinfo.ISiteInfo
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class SiteInfoShutterStock: ISiteInfo {
    override val name: String
        get() = "shutterstock"

    override fun getSearchKeyword(flowerInfo: FlowerInfo): String = flowerInfo.eng16

    //https://stackoverflow.com/questions/21713280/find-div-element-by-multiple-class-names
    override fun getImgElements(driver: WebDriver): List<WebElement> {
        return driver
//            .findElementsWhileNotEmpty(By.className("jss198"))[0]
            .findElementsWhileNotEmpty(By.tagName("img"))
            .asSequence()
            .filter { "shutterstock" != it.getAttribute("alt") }
            .drop(2)
            .toList()
    }

    override fun getSearchLink(keyword: String) = "https://www.shutterstock.com/ko/search/$keyword"

    override fun doAfterGetLink(driver: WebDriver) {

    }
}