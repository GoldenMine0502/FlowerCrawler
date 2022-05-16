package kr.goldenmine

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kr.goldenmine.network.RetrofitFactory
import org.jsoup.Jsoup
import java.io.File

fun main() {
    val map = HashMap<Int, String>()
    val idFile = File("ids.txt")
    val saveFile = File("flowers.json")
    val gson = Gson()
//    val type = object : TypeToken<HashMap<String, Int>>() {}.type

    val list = ArrayList<FlowerInfo>()

    if(idFile.exists()) {
        println(idFile.readText())
        idFile.readText().split("\n").forEach {
            val split = it.split(",")
            if(split.size >= 2)
                map[split[1].toInt()] = split[0]
        }
    }

    println(map)

    map.forEach { (t, u) ->
        val info = requestSpec(t)
        println(info.name)
        info.name = u
        list.add(info)
    }

    println(list.size)

    saveFile.bufferedWriter().use {
        gson.toJson(list, it)
    }
}

fun requestSpec(id: Int): FlowerInfo {
    val result = RetrofitFactory.getFlowerInstance().findSpec(serviceKey, id).execute().body()!!

    val html = Jsoup.parse(result)

    /*
        int plantId; // plantPilbkNo
    String spot; // 주요 분포지역 dstrb
    String family; // 과 familyKorNm
    String genus; // 속 genusKorNm
    String name; // 이름 plantGnrlNm
    String shape; // 모양 shpe

    String descBreed; // 번식방법 brdMthdDesc
    String descReason; // 재배이유 farmSpftDesc
    String descBranch; // 상세설명 가지 branchDesc
    String descTime; // 상세설명 시기 flwrDesc
    String descFruit; // 상세설명 열매 fritDesc
    String descLeaf; // 상세설명 잎파리 leafDesc
    String descRoot; // 상세설명 뿌리 rootDesc
    String descStem; // 상세설명 줄기 stemDesc
    String descUse; // 상세설명 활용용도 useMthdDesc
    String descSimilar; // 상세설명 비슷한 종류 smlrPlntDesc
    String descGrow; // 재배방법/성장 등등 grwEvrntDesc

    String size; // 크기 sz

    String imgURL; // 이미지 URL imgUrl
     */

    val flowerInfo = FlowerInfo()
    flowerInfo.text = html.getElementsByTag("item")[0].text()
    flowerInfo.plantId = id
    flowerInfo.spot = html.getElementsByTag("dstrb")[0].text()
    flowerInfo.family = html.getElementsByTag("familyKorNm")[0].text()
    flowerInfo.genus = html.getElementsByTag("genusKorNm")[0].text()
    flowerInfo.name = html.getElementsByTag("plantGnrlNm")[0].text()
    flowerInfo.shape = html.getElementsByTag("shpe")[0].text()

    flowerInfo.descBreed = html.getElementsByTag("brdMthdDesc")[0].text()
    flowerInfo.descReason = html.getElementsByTag("farmSpftDesc")[0].text()
    flowerInfo.descBranch = html.getElementsByTag("branchDesc")[0].text()
    flowerInfo.descTime = html.getElementsByTag("flwrDesc")[0].text()
    flowerInfo.descFruit = html.getElementsByTag("fritDesc")[0].text()
    flowerInfo.descLeaf = html.getElementsByTag("leafDesc")[0].text()
    flowerInfo.descRoot = html.getElementsByTag("rootDesc")[0].text()
    flowerInfo.descStem = html.getElementsByTag("stemDesc")[0].text()
    flowerInfo.descUse = html.getElementsByTag("useMthdDesc")[0].text()
    flowerInfo.descSimilar = html.getElementsByTag("smlrPlntDesc")[0].text()
    flowerInfo.descGrow = html.getElementsByTag("grwEvrntDesc")[0].text()

    return flowerInfo
}