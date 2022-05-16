package kr.goldenmine.util

import kr.goldenmine.siteinfo.FlowerInfo
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import java.io.FileInputStream

fun loadFlowersFromExcel(): List<FlowerInfo> {
    val flowers = ArrayList<FlowerInfo>()
//    val eng22Map = HashMap<String, String>()
//    val eng16Map = HashMap<String, String>()
    FileInputStream("flower.xls").use { input ->
        val wb = HSSFWorkbook(input)

        val sheet = wb.getSheetAt(0)
//        for (row in sheet) {
//            println(row.getCell(20)?.stringCellValue)
//        }

//        flowers.addAll(sheet.asSequence()
//            .drop(1)
//            .mapNotNull { it.getCell(16)?.stringCellValue }
//            .filter { it.isNotEmpty() }
//            .map { it.substring(0, it.length - 1) }
//            .distinct()
//            .filter { it.isNotEmpty() }
////            .filter { (File("images2/$it").listFiles()?.size ?: 0) < 95 }
//            .toList())

        var recentKor: String? = null

        sheet
            .drop(1)
//            .mapNotNull { it.getCell(17)?.stringCellValue }
//            .filter { it.isNotEmpty() }
            .forEach { row ->
                // 18 = 속국명
                val kor = row.getCell(18)?.stringCellValue
                if (kor != null && kor.length >= 2) {
                    recentKor = kor.substring(0, kor.length - 1)
                }

                // 16 = 속명
                // 22 = 추천영문명
                val eng22 = row.getCell(22)?.stringCellValue
                val eng16 = row.getCell(16)?.stringCellValue


                if(recentKor != null && eng22 != null && eng16 != null && recentKor!!.isNotEmpty() && eng22.isNotEmpty() && eng16.isNotEmpty()) {
                    val flower = FlowerInfo(recentKor!!, eng22, eng16)

                    if(flowers.firstOrNull { eng16 == it.eng16 } == null) {
                        flowers.add(flower)
                    }
                }
            }
    }

    return flowers
}