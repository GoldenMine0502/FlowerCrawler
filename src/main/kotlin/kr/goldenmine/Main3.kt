package kr.goldenmine

import com.google.gson.reflect.TypeToken
import kr.goldenmine.network.RetrofitFactory
import org.jsoup.Jsoup
import java.io.File
import java.lang.Thread.sleep

const val serviceKey = "Eojpb8YSWns0To%2BoSRzqiOiX8V0eaT7Nnsd%2B9mkWJqTr1vy%2Fw8S3oj8M5v303ljFcVOXMn5cr09BrqaN3fnXSg%3D%3D"

fun main() {
    //일반 인증키
    //(Encoding)
    //Eojpb8YSWns0To%2BoSRzqiOiX8V0eaT7Nnsd%2B9mkWJqTr1vy%2Fw8S3oj8M5v303ljFcVOXMn5cr09BrqaN3fnXSg%3D%3D
    //일반 인증키
    //(Decoding)
    //Eojpb8YSWns0To+oSRzqiOiX8V0eaT7Nnsd+9mkWJqTr1vy/w8S3oj8M5v303ljFcVOXMn5cr09BrqaN3fnXSg==

//    val gson = Gson()
    val map = HashMap<String, Int>()
    val saveFile = File("ids.txt")
    val type = object : TypeToken<HashMap<String, Int>>() {}.type

    if(saveFile.exists()) {
        println(saveFile.readText())
        saveFile.readText().split("\n").forEach {
            val split = it.split(",")
            if(split.size >= 2)
                map[split[0]] = split[1].toInt()
        }
    }

    val list = File("images2").listFiles()?.filter { !map.containsKey(it.name) }?.toList()!!

    println(list.size)

    sleep(1000)

    list.forEach { folder ->
        try {
            val name = folder.name
            val result = requestId(name)

            map[name] = result

            println("$name = $result")

            if(!saveFile.exists()) saveFile.createNewFile()

            saveFile.bufferedWriter().use {
                map.forEach { (t, u) ->
                    it.write("$t,$u\n")
                }
            }
        } catch(ex: Exception) {
            ex.printStackTrace()
            println("${folder.name} not exist")
        }
    }


}

fun requestId(name: String): Int {
    val result = RetrofitFactory.getFlowerInstance().findId(1, name, serviceKey, 1, 1).execute().body()!!

    return Jsoup.parse(result).selectFirst("plantPilbkNo")?.text()?.toInt()!!
}