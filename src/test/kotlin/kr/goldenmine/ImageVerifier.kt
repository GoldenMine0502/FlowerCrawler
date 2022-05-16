package kr.goldenmine

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.lang.Double.max
import java.nio.Buffer
import java.util.*
import javax.imageio.ImageIO
import kotlin.math.roundToInt

fun main() {
    val folder = File("images3/")
    val allfiles =
        folder.listFiles()?.asSequence()?.flatMap { it.listFiles()?.toList() ?: Collections.emptyList() }?.toList()
            ?: Collections.emptyList()

    var count = 0

    var deletedCount = 0

    allfiles.forEach {
        try {
            val img = ImageIO.read(it)
            if (img.width < 80 || img.height < 80) {
                println("size too small: ${it.path} $count")
                deletedCount++
//                it.delete()
            }

            if (max(img.width.toDouble() / img.height, img.height.toDouble() / img.width) > 3.5) {
                println("ratio not valid ${it.path} $count")
                deletedCount++
//                it.delete()
            }

            // 그레이 비율이 95%이상
            val grayRatio = getGrayRatio(img, 2)
            if(grayRatio > 0.99) {
                println("gray image ${it.path} $count $grayRatio ${(deletedCount.toDouble() / count * 1000).roundToInt() / 10.0}%")
                deletedCount++
//                it.delete()
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            println("exception ${it.path} $count")
            deletedCount++
//            it.delete()
        }

        count++
    }

    println("$deletedCount")
}

fun getGrayRatio(bufferedImage: BufferedImage, n: Int = 5): Float {
    val pixels = bufferedImage.width * bufferedImage.height
    var graypixels = 0
    for(x in 0 until bufferedImage.width) {
        for(y in 0 until bufferedImage.height) {
            if(isGray(Color(bufferedImage.getRGB(x, y)), n)) {
                graypixels++
            }
        }
    }

    return graypixels.toFloat() / pixels
}

fun isGray(color: Color, n: Int): Boolean {
    val R = color.red
    val G = color.green
    val B = color.blue

    val R2 = R / 255.0
    val G2 = G / 255.0
    val B2 = B / 255.0

    val K = 1 - max(max(R2, G2), B2)
    val C = (1 - R2 - K) / (1 - K)
    val M = (1 - G2 - K) / (1 - K)
    val Y = (1 - B2 - K) / (1 - K)

    return (C + M + Y) * 100 < n
}