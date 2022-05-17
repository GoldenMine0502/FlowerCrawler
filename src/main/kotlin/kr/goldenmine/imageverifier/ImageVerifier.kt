package kr.goldenmine.imageverifier

import kr.goldenmine.util.CipherUtil
import kr.goldenmine.util.moveFile
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.util.*
import javax.imageio.ImageIO


class ImageVerifier(private val rootFolder: File, private val verifiers: List<IVerifier>) {
    // 중복 제거를 위한 md5
    val base64List = HashSet<String>()

    fun verifyAll() {
        val allfiles = rootFolder.listFiles()
            ?.asSequence()
            ?.flatMap { it.listFiles()?.toList() ?: Collections.emptyList() }
            ?.toList() ?: Collections.emptyList()

        var count = 0

        var deletedCount = 0

        allfiles.forEach { file ->
            val imageBase64 = CipherUtil.getBase64FromImage(file)
//            val imageBase64 = CipherUtil.getMD5Checksum(file)
//            println(md5)

            val failed = try {
                val img = ImageIO.read(file)

                if(!base64List.contains(imageBase64)) {
                    base64List.add(imageBase64)

                    val verifier = verifiers.firstOrNull { it.verify(file, img) }

                    if (verifier != null) {
                        println("failed ${verifier.name} ${file.path} $count")


                        true
                    } else {
                        false
                    }
                } else { // md5가 겹치는경우
                    println("base64 doubled ${file.path} $count")

                    true
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                println("exception ${file.path} $count")

                true
            }

            if(failed) {
                deletedCount++

                val dst = File("deleted/${file.parentFile.name}/${file.name}")
                moveFile(file, dst)
            }

            count++
        }

        println("$deletedCount")
    }


}