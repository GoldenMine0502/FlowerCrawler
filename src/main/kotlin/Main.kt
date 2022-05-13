import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeDriver
import java.io.*
import java.lang.Thread.sleep
import java.net.URL
import javax.imageio.ImageIO
import javax.xml.bind.DatatypeConverter
import kotlin.math.roundToInt


fun main() {
    setChromePath("lib/chromedriver.exe")

    val list = getFlowers()

    println(list)
    println(list.size)

    downloadImages("images", list)
}

fun getFlowers(): List<String> {
    return FileInputStream("flower.xls").use { input ->
        val wb = HSSFWorkbook(input)

        val sheet = wb.getSheetAt(0)
//        for (row in sheet) {
//            println(row.getCell(20)?.stringCellValue)
//        }

        sheet.asSequence().drop(1).mapNotNull { it.getCell(20)?.stringCellValue }.distinct().filter {
            val file = File("images/$it")

            !file.exists() || file.listFiles() == null || (file.listFiles() != null && file.listFiles().size < 30)
        }.toList()
    }
}

fun downloadImages(folderStr: String, flowers: List<String>) {
    val driver = ChromeDriver()

    val searchImgPer = 30
    val start = System.currentTimeMillis()
    var completed = 0

    flowers.forEach { keyword ->
        val current = System.currentTimeMillis()

        if (completed > 0) {
            val averageTime = (current - start) / completed
            val remaining = flowers.size - completed

            println("completed: ($completed/${flowers.size}) (${(completed.toDouble() / flowers.size * 100).roundToInt()}%) remaining time: ${(averageTime * remaining / 1000.0 / 60.0).roundToInt()} min.")
        }

        driver.get(getGoogleImageSearchLink("$keyword 식물"))

        sleep(500)

        val folder = File("$folderStr/$keyword")
        folder.mkdirs()

        // 이미지 목록 div, img 목록
        val imgList = driver.findElement(By.className("mJxzWe")).findElements(By.tagName("img"))

        var count = 0

        imgList.take(searchImgPer).forEach { imgElement ->
            try {
                val data = imgElement.getAttribute("src")

                val file = File(folder, "img$count.jpg")

                if (!file.exists()) file.createNewFile();

//            println("data: $data")

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
//                    println("downloading image.. $data")
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
            } catch (ex: Exception) {
                ex.printStackTrace()
                println("an error occured. keep downloading...")
            }

        }
        completed++
    }

    driver.quit()
}

fun getGoogleImageSearchLink(keyword: String) = "https://www.google.com/search?q=${keyword}&source=lnms&tbm=isch"

fun setChromePath(path: String) {
    val chrome = File(path)
    System.setProperty("webdriver.chrome.driver", chrome.absolutePath)
}