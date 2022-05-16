package kr.goldenmine.imageverifier

import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import javax.imageio.ImageIO


class ImageVerifier(private val rootFolder: File, private val verifiers: List<IVerifier>) {
    fun verifyAll() {
        val allfiles = rootFolder.listFiles()
            ?.asSequence()
            ?.flatMap { it.listFiles()?.toList() ?: Collections.emptyList() }
            ?.toList() ?: Collections.emptyList()

        var count = 0

        var deletedCount = 0

        allfiles.forEach { file ->
            val failed = try {
                val img = ImageIO.read(file)

                val verifier = verifiers.firstOrNull { it.verify(file, img) }

                if (verifier != null) {
                    println("failed ${verifier.name} ${file.path} $count")


                    true
                } else {
                    false
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

    fun moveFile(src: File, dst: File) {
        try {
            dst.parentFile.mkdirs()

            val filePath = src.toPath()
            val filePathToMove = dst.toPath()
            Files.move(filePath, filePathToMove)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}