package kr.goldenmine.util

import java.io.File
import java.io.IOException
import java.nio.file.Files

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