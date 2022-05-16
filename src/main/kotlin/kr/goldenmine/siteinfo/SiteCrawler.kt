package kr.goldenmine.siteinfo

import org.openqa.selenium.Dimension
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import java.awt.Image
import java.awt.image.BufferedImage
import java.io.*
import java.lang.Thread.sleep
import java.net.URL
import javax.imageio.ImageIO
import javax.xml.bind.DatatypeConverter

class SiteCrawler(
    private val rootFolder: File,

    private val siteInfo: ISiteInfo,

    private val list: List<FlowerInfo>,

    var imageCountPerKeyword: Int = -1
) {
    val driver: WebDriver = ChromeDriver()

    var sleepAfterGetLink: Long = 1000


    // 처음 한번은 chromedriver.exe의 위치를 지정해야함
    companion object {
        init {
            val chrome = File("lib/chromedriver.exe")
            System.setProperty("webdriver.chrome.driver", chrome.absolutePath)
        }
    }

    init {
        val defaultDimension = Dimension((1920 * 1.2).toInt(), (1080 * 1.2).toInt())

        driver.manage().window().size = defaultDimension
    }

    // 쓰레드 관련 관리를 편하게 하기 위함
    fun downloadAll() {
        var failCount = 0

        list.forEach { flowerInfo ->
            // 키워드에 대한 링크 얻기
            val keyword = siteInfo.getSearchKeyword(flowerInfo)
            val link = siteInfo.getSearchLink(keyword)

            // 폴더 만들기, 결과적으로 폴더는 항상 한국어로
            val folder = File(rootFolder, "/${flowerInfo.korean}")
            folder.mkdirs()

            // 해당 링크로 접속
            driver.get(link)
            siteInfo.doAfterGetLink(driver)

            sleep(sleepAfterGetLink)

            // 가능한 이미지 엘리먼트 얻기
            val imgElements = siteInfo.getImgElements(driver)

            for (i in imgElements.indices) {
                try {
                    // 일정 갯수 이상 다운로드시 break
                    if (imageCountPerKeyword >= 0 && i >= imageCountPerKeyword) break

                    val imgElement = imgElements[i]
                    val data = imgElement.getAttribute("src")

                    // 파일 생성
                    val file = File(folder, "/${siteInfo.name}_${keyword}_$i.jpg")
                    if (!file.exists()) file.createNewFile()

                    // 이미지 얻기
                    val img = getImageFromSrcData(data)

                    // 이미지 저장하기
                    file.outputStream().buffered().use {
                        ImageIO.write(img, "jpg", it)
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()

                    failCount++

                    println("failed to download $i of ($flowerInfo) from ${siteInfo.name} fail count: $failCount")

                }
            }
        }
    }

    fun getImageFromSrcData(data: String): BufferedImage {
        val split = data.split(",")

        return if (split.size >= 2) { // true일시 base64 인코딩
            val base64Image: String = split[1]
            val imageBytes = DatatypeConverter.parseBase64Binary(base64Image)

            ImageIO.read(ByteArrayInputStream(imageBytes))
        } else {
            BufferedInputStream(URL(data).openStream()).use { input ->
                ImageIO.read(input)
            }
        }
    }
}