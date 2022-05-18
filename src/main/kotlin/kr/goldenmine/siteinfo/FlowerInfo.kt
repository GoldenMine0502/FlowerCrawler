package kr.goldenmine.siteinfo

class FlowerInfo(val korean: String, val eng16: String, val eng19: String, val eng22: String) {
    override fun toString(): String {
        return "kor: $korean, eng16: $eng16, eng19: $eng19, eng22: $eng22"
    }
}