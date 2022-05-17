package kr.goldenmine

import kr.goldenmine.imageverifier.*
import kr.goldenmine.siteinfo.SiteCrawler
import kr.goldenmine.siteinfo.google.SiteInfoGoogleEng
import kr.goldenmine.siteinfo.google.SiteInfoGoogleEngSpec
import kr.goldenmine.siteinfo.google.SiteInfoGoogleKor
import kr.goldenmine.siteinfo.naver.SiteInfoNaver
import kr.goldenmine.siteinfo.shutterstock.SiteInfoShutterStock
import kr.goldenmine.util.loadFlowersFromExcel
import java.io.File
import java.lang.Thread.sleep

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

    println("${flowers.size} flowers will be downloaded.")

    val folder = File("images4/")

    folder.mkdirs()

//    val crawlers = listOf(
//        SiteCrawler(folder, SiteInfoGoogleEng(excepts), flowers, 50, true, 3000),
//        SiteCrawler(folder, SiteInfoGoogleKor(excepts), flowers, 50, true, 3000),
//        SiteCrawler(folder, SiteInfoNaver(excepts), flowers, 50, true, 5000),
//        SiteCrawler(folder, SiteInfoShutterStock(), flowers, 50, true),
//    )

    val thread1 = Thread {
        SiteCrawler(folder, SiteInfoGoogleEng(excepts), flowers, 50, true, 1000).downloadAll()
    }

    val thread2 = Thread {
        SiteCrawler(folder, SiteInfoGoogleKor(excepts), flowers, 50, true, 1000).downloadAll()
    }

    val thread3 = Thread {
        SiteCrawler(folder, SiteInfoNaver(excepts), flowers, 50, true, 5000).downloadAll()
    }

    val thread4 = Thread {
        SiteCrawler(folder, SiteInfoShutterStock(), flowers, 50, true).downloadAll()
    }

    val thread5 = Thread {
        SiteCrawler(folder, SiteInfoGoogleEngSpec(excepts), flowers, 50, true).downloadAll()
    }

    thread1.start()
    sleep(1000)

    thread3.start()
    sleep(1000)

    thread4.start()
    sleep(1000)

    thread1.join()

    thread2.start()
    sleep(1000)

    thread2.join()

    thread5.start()
    sleep(1000)

    thread3.join()
    thread4.join()
    thread5.join()

    val verifiers = listOf(
        VerifierImageGrayScaled(),
        VerifierImageRatio(),
        VerifierImageSmall()
    )

    val imageVerifier = ImageVerifier(folder, verifiers)

    imageVerifier.verifyAll()
}