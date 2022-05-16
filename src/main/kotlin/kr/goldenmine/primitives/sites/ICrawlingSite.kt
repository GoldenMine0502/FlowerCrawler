package kr.goldenmine.primitives.sites

import kr.goldenmine.downloadFromImgSrc
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import java.io.File
import java.lang.Thread.sleep

interface ICrawlingSite {
    val folder: File

    val searchImgPer: Int

    val name: String

    fun getDriver(): WebDriver

    fun getSearchLink(keyword: String): String

    fun getImgElements(): List<WebElement>

    fun pastDoing()

    fun useKeyword(kor: String, eng22: String?, eng16: String?): String

    fun downloadFromLink(kor: String, eng22: String?, eng16: String?, skips: List<String>, sleepFirst: Long = 1000, sleepSecond: Long = 1) {
        try {
            val link = getSearchLink(useKeyword(kor, eng22, eng16))
            val driver = getDriver()

            driver.get(link)
            pastDoing()
            sleep(sleepFirst)

            val imgList = getImgElements()

            var count = 0

            imgList.take(searchImgPer).forEach { imgElement ->
                val f = File(folder, "/$kor")
                f.mkdirs()
                val file = File(f, "$name$eng16$count.jpg")
                if (!file.exists()) file.createNewFile()

                if (!downloadFromImgSrc(imgElement, file, skips))
                    println("data is null. skipping... $count $kor $name")
                else
                    count++

                sleep(sleepSecond)
            }
        } catch(ex: Exception) {
            ex.printStackTrace()
        }
    }
}