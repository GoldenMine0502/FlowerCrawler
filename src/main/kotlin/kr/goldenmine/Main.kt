package kr.goldenmine

import kr.goldenmine.siteinfo.SiteCrawler
import kr.goldenmine.siteinfo.google.SiteInfoGoogleEng
import kr.goldenmine.siteinfo.google.SiteInfoGoogleKor
import kr.goldenmine.siteinfo.naver.SiteInfoNaver
import kr.goldenmine.siteinfo.shutterstock.SiteInfoShutterStock
import kr.goldenmine.util.loadFlowersFromExcel
import java.io.File

fun main() {
    val excepts = ArrayList<String>()

    // 쓸데없는 분석 차트가 많음
    excepts.add("researchgate.net")
    excepts.add("sciencedirect.com")
    excepts.add("academic.edu")
    excepts.add("peerj.com")
    excepts.add("nature.com")

    // 썸네일이 별로 썩 좋지 못함
    excepts.add("youtube.com")
    excepts.add("facebook.com")
    excepts.add("twitter.com")

    // 쇼핑몰들은 대표적으로 사진 정보가 좋지 못하다
    excepts.add("shopping.naver.com")
    excepts.add("auction.co.kr")
    excepts.add("interpark.com")
    excepts.add("gmarket.co.kr")
    excepts.add("amazon.com")
    excepts.add("ninelife.hu")

    // 흑백 이미지 위주
    excepts.add("etc.usf.edu")
//    excepts.add("pixtastock.")

    // 벡터 이미지
    excepts.add("vectorstock.com")

    // 이상한 한국 사이트?
    excepts.add("korea.kr")
    excepts.add("forest.go.kr")

    // 블로그를 걸러야하나...
    excepts.add("blog.naver.com")
    excepts.add("blog.daum.net")
    excepts.add("tistory.com")

    // 뉴스 사이트
    excepts.add("ttcnews.kr")

    // 기타 이유
    excepts.add("alamy.com")
    excepts.add("wisdom.prkorea.com")
    excepts.add("extension.uga.edu")
    excepts.add("freshkorean.com")
    excepts.add("wordrow.kr")
    excepts.add("wikiwand.com")
    excepts.add("studylib.net")
    excepts.add("creationwiki.org")


    val flowers = loadFlowersFromExcel()
    val folder = File("images4/")

    folder.mkdirs()

    val crawlers = listOf(
        SiteCrawler(folder, SiteInfoGoogleEng(excepts), flowers, 25),
//        SiteCrawler(folder, SiteInfoGoogleKor(excepts), flowers, 25),
//        SiteCrawler(folder, SiteInfoNaver(excepts), flowers, 25),
//        SiteCrawler(folder, SiteInfoShutterStock(), flowers, 25),
    )

    crawlers.forEach {
//        Runtime.getRuntime().addShutdownHook(Thread() {
//            it.driver.quit()
//        })
        Thread {

            it.downloadAll()
        }.start()
    }
}