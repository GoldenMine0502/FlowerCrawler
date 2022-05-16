package kr.goldenmine.primitives

import kr.goldenmine.siteinfo.FlowerInfo
import kr.goldenmine.primitives.sites.CrawlerGoogle
import kr.goldenmine.primitives.sites.CrawlerNaver
import kr.goldenmine.primitives.sites.CrawlerShutterStock
import kr.goldenmine.primitives.sites.ICrawlingSite
import java.io.*

class FlowerCrawler(
    private val keywords: List<FlowerInfo>,
    )
{

    val sites = ArrayList<ICrawlingSite>()

    init {
        val folder = File("images3")
        folder.mkdirs()

        sites.add(CrawlerGoogle(folder, 30, "google"))
        sites.add(CrawlerNaver(folder, 30, "naver"))
        sites.add(CrawlerShutterStock(folder, 30, "stock"))
    }

    fun downloadFromAll() {
        val excepts = ArrayList<String>()
        excepts.add("researchgate.net")
        excepts.add("youtube.com")
        excepts.add("facebook.com")
        excepts.add("alamy.com")
        excepts.add("wisdom.prkorea.com")
        excepts.add("extension.uga.edu")
        excepts.add("freshkorean.com")
        excepts.add("extension.uga.edu")
        excepts.add("shopping.naver.com")


        sites.map { site ->
            val thread = Thread {
                val start = System.currentTimeMillis()
                var count = 0
                keywords.forEach {
                    val kor = it.korean
                    val eng22 = it.eng22
                    val eng16 = it.eng16

                    try {
                        site.downloadFromLink(kor, eng22, eng16, excepts, sleepFirst = 500)
                    } catch(ex: Exception) {
                        ex.printStackTrace()
                    }

                    count++
                    val time = System.currentTimeMillis() - start
                    val totalTime = time / count * keywords.size

                    if(count%10 == 0) {
                        println("${site.name} ($count/${keywords.size}) remaining time: ${(totalTime - time) / 60000} min")
                    }
                }
//                translate22.keys.forEach { eng22 ->
//                    val value1 = translate22[eng22]
//                    val value2 = translate16
//
//                    try {
//                        site.downloadFromLink(eng22, value1, value2, excepts, sleepFirst = 700)
//                    } catch(ex: Exception) {
//                        ex.printStackTrace()
//                    }
//                }
//                translateMap.forEach { (key, value) ->
//                    println("$key $value ${site.name}")
//                    try {
//                        site.downloadFromLink(key, value, excepts, sleepFirst = 5000)
//                    } catch(ex: Exception) {
//                        ex.printStackTrace()
//                    }
//                }
            }
            thread.start()

            thread
        }.forEach { it.join() }
    }
}

