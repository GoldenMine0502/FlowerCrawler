package kr.goldenmine.primitives

import kr.goldenmine.imageverifier.ImageVerifier
import kr.goldenmine.imageverifier.VerifierImageGrayScaled
import kr.goldenmine.imageverifier.VerifierImageRatio
import kr.goldenmine.imageverifier.VerifierImageSmall
import java.io.File

fun main() {
    val folder = File("images4/")

    val verifiers = listOf(
        VerifierImageGrayScaled(),
        VerifierImageRatio(),
        VerifierImageSmall()
    )

    val imageVerifier = ImageVerifier(folder, verifiers)

    imageVerifier.verifyAll()
}

//
//import java.awt.Color
//import java.awt.image.BufferedImage
//import java.io.File
//import java.lang.Double.max
//import java.util.
//import javax.imageio.ImageIO
//import kotlin.math.roundToInt
//
//fun main() {
//    val folder = File("images3/")
//    val allfiles =
//        folder.listFiles()?.asSequence()?.flatMap { it.listFiles()?.toList() ?: Collections.emptyList() }?.toList()
//            ?: Collections.emptyList()
//
//    var count = 0
//
//    var deletedCount = 0
//
//    allfiles.forEach {
//        try {
//            val img = ImageIO.read(it)
//            if (img.width < 80 || img.height < 80) {
//                println("size too small: ${it.path} $count")
//                deletedCount++
////                it.delete()
//            }
//
//            if (max(img.width.toDouble() / img.height, img.height.toDouble() / img.width) > 3.5) {
//                println("ratio not valid ${it.path} $count")
//                deletedCount++
////                it.delete()
//            }
//
//            // 그레이 비율이 95%이상
//            val grayRatio = getGrayRatio(img, 2)
//            if(grayRatio > 0.99) {
//                println("gray image ${it.path} $count $grayRatio ${(deletedCount.toDouble() / count * 1000).roundToInt() / 10.0}%")
//                deletedCount++
////                it.delete()
//            }
//        } catch (ex: Exception) {
//            ex.printStackTrace()
//            println("exception ${it.path} $count")
//            deletedCount++
////            it.delete()
//        }
//
//        count++
//    }
//
//    println("$deletedCount")
//}
//
