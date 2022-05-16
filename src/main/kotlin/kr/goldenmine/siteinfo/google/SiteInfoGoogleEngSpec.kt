package kr.goldenmine.siteinfo.google

import kr.goldenmine.siteinfo.FlowerInfo

class SiteInfoGoogleEngSpec(override val skipSites: List<String>) : SiteInfoGoogle(skipSites) {
    override val name: String
        get() = "google_eng_s"

    override fun getSearchKeyword(flowerInfo: FlowerInfo): String = "${flowerInfo.eng16}"
}