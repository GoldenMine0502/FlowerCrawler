package kr.goldenmine.primitives

import kr.goldenmine.util.moveFile
import java.io.File
import java.util.*

fun main() {
    val folder = File("images4/")

    val allfiles = folder.listFiles()
        ?.asSequence()
        ?.flatMap { it.listFiles()?.toList() ?: Collections.emptyList() }
        ?.toList() ?: Collections.emptyList()

    allfiles.forEach { file ->
        if(file.name.contains("shutterstock") || file.name.contains("google_eng_s")) {
            val dst = File("deleted/${file.parentFile.name}/${file.name}")
            moveFile(file, dst)

            println("moved ${file.name}")
        }
    }
}