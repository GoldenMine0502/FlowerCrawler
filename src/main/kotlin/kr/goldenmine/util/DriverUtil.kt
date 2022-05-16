package kr.goldenmine.util

import org.openqa.selenium.*
import java.io.*
import java.lang.Thread.sleep
import java.net.URL
import javax.imageio.ImageIO
import javax.xml.bind.DatatypeConverter

fun setChromePath(path: String) {
    val chrome = File(path)
    System.setProperty("webdriver.chrome.driver", chrome.absolutePath)
}

fun getDefaultDimension() = Dimension((1920 * 1.2).toInt(), (1080 * 1.2).toInt())

fun SearchContext.findElementsWhileNotEmpty(by: By, sleep: Long = 1000, timeout: Long = 2500): List<WebElement> {
    var result: List<WebElement>?

    sleep(sleep)

    var start = System.currentTimeMillis()

    while (true) {
        result = findElements(by)

        if(result != null && result.isNotEmpty()) break

        sleep(sleep)

        if(timeout >= 0 && (System.currentTimeMillis() - start) >= timeout) throw RuntimeException("cannot find any elements: $by")
    }

    return result!!
}

fun downloadFromImgSrc(element: WebElement, file: File, skips: List<String>): Boolean {
    try {
        val data = element.getAttribute("src")

        if(skips.any { it.contains(data) }) return false

        if (data != null) {
            val split = data.split(",")
            if (split.size >= 2) { // true일시 base64 인코딩
                val base64Image: String = split[1]
                val imageBytes = DatatypeConverter.parseBase64Binary(base64Image)

                val img = ImageIO.read(ByteArrayInputStream(imageBytes))

                file.outputStream().buffered().use {
                    ImageIO.write(img, "png", it)
                }

                img.flush()
            } else {
//                println("downloading image.. $data")
                // 이미지 다운로드
                BufferedInputStream(URL(data).openStream()).use { input ->
                    BufferedOutputStream(FileOutputStream(file)).use { output ->
                        input.copyTo(output)
                    }
                }
            }
            return true
        } else {
            return false
        }
    } catch(ex: Exception) {
        ex.printStackTrace()
        return false
    }
}