import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities
import java.io.File
import java.lang.Thread.sleep

fun main() {
    setChromePath("lib/chromedriver.exe")

    val flowers = listOf("해바라기", "장미")

    val driver = ChromeDriver()

    flowers.forEach {
        driver.get(getGoogleImageSearchLink("$it 사진"))
        sleep(1000L)


        val folder = File("images/${it}")
        folder.mkdirs()

        // 이미지 목록 div, img 목록
        driver.findElement(By
                .className("mJxzWe"))
                .findElement(By.tagName("img"))


    }
}

fun getGoogleImageSearchLink(keyword: String) = "https://www.google.com/search?q=${keyword}&source=lnms&tbm=isch"

fun setChromePath(path: String) {
    val chrome = File(path)
    System.setProperty("webdriver.chrome.driver", chrome.absolutePath)
}