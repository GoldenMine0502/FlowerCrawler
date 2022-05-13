package kr.goldenmine

import java.io.File
import javax.imageio.ImageIO

fun main() {
    val folder = File("images2/")
    val allfiles = folder.listFiles().asSequence().flatMap { it.listFiles().toList() }.toList()

    var count = 0

    allfiles.forEach {
        try {
            val img = ImageIO.read(it)
            if(img.width < 10 || img.height < 10) {
                println("size too small: ${it.path}")
                it.delete()
            }
        } catch(ex: Exception) {
//            ex.printStackTrace()
            println("exception ${it.path} $count")
            it.delete()
        }

        count++
    }
}