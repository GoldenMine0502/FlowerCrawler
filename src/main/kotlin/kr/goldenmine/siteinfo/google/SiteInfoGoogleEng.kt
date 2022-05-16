package kr.goldenmine.siteinfo.google

import kr.goldenmine.siteinfo.FlowerInfo

class SiteInfoGoogleEng(override val skipSites: List<String>) : SiteInfoGoogle(skipSites) {
    override val name: String
        get() = "google_eng"

    override fun getSearchKeyword(flowerInfo: FlowerInfo): String = "${flowerInfo.eng22} plant"
}