package kr.goldenmine

import com.google.gson.Gson
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import java.io.File

fun main() {
    val map = HashMap<Int, String>()
//    val idFile = File("ids.txt")
    val saveFile = File("flowers.csv")
    val gson = Gson()

//    if(idFile.exists()) {
//        println(idFile.readText())
//        idFile.readText().split("\n").forEach {
//            val split = it.split(",")
//            if(split.size >= 2)
//                map[split[1].toInt()] = split[0]
//        }
//    }

    val past = File("images2").listFiles().filter { it.isDirectory }.toList()
    val list = past.toList().sorted()
    val sb = StringBuilder()

    list.forEach {
        sb.append(it.name)
        sb.append("\n")
    }

    if(!saveFile.exists()) {
        saveFile.writeText(sb.toString(), Charsets.UTF_8)
    }
//
//    val wb = HSSFWorkbook(input)
//
//    val sheet = wb.getSheetAt(0)
}