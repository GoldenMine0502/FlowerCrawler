package kr.goldenmine.siteinfo.google

import kr.goldenmine.siteinfo.FlowerInfo

class SiteInfoGoogleKor(override val skipSites: List<String>) : SiteInfoGoogle(skipSites) {
    override val name: String
        get() = "google_kor"

    override fun getSearchKeyword(flowerInfo: FlowerInfo): String = "${flowerInfo.korean} 식물"
}