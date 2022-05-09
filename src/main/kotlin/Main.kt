import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeDriver
import java.io.*
import java.lang.Thread.sleep
import java.net.URL
import javax.imageio.ImageIO
import javax.xml.bind.DatatypeConverter


fun main() {
    setChromePath("lib/chromedriver.exe")

    val list = FileInputStream("flower.xls").use { input ->
        val wb = HSSFWorkbook(input)

        val sheet = wb.getSheetAt(0)
//        for (row in sheet) {
//            println(row.getCell(20)?.stringCellValue)
//        }

        sheet.asSequence().drop(1).mapNotNull { it.getCell(20)?.stringCellValue }.distinct().toList()
    }

    println(list)

    downloadImages(list)
}

fun downloadImages(flowers: List<String>) {
    val driver = ChromeDriver()

    val searchImgPer = 10

    flowers.forEach { keyword ->
        driver.get(getGoogleImageSearchLink("$keyword 사진"))
        sleep(500L)

        val folder = File("images/$keyword")
        folder.mkdirs()

        // 이미지 목록 div, img 목록
        val imgList = driver.findElement(By.className("mJxzWe")).findElements(By.tagName("img"))

        var count = 0

        imgList.take(searchImgPer).forEach { imgElement ->
            val data = imgElement.getAttribute("src")

            val file = File(folder, "img$count.jpg")

            if (!file.exists()) file.createNewFile();

            println("data: $data")

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
                    println("downloading image.. $data")
                    // 이미지 다운로드
                    BufferedInputStream(URL(data).openStream()).use { input ->
                        BufferedOutputStream(FileOutputStream(file)).use { output ->
                            input.copyTo(output)
                        }
                    }
                }

                count++
            } else {
                println("data is null. skipping... $count")
            }

        }

        sleep(500L)
    }

    driver.quit()
}

fun getGoogleImageSearchLink(keyword: String) = "https://www.google.com/search?q=${keyword}&source=lnms&tbm=isch"

fun setChromePath(path: String) {
    val chrome = File(path)
    System.setProperty("webdriver.chrome.driver", chrome.absolutePath)
}