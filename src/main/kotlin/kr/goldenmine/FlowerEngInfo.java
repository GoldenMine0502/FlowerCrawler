package kr.goldenmine;

public class FlowerEngInfo {
    private String korean;
    private String eng22;
    private String eng16;

    public FlowerEngInfo(String korean, String eng22, String eng16) {
        this.korean = korean;
        this.eng22 = eng22;
        this.eng16 = eng16;
    }

    public String getKorean() {
        return korean;
    }

    public String getEng16() {
        return eng16;
    }

    public String getEng22() {
        return eng22;
    }

    @Override
    public String toString() {
        return "kor: " + korean + ", eng22: " + eng22 + ", eng16: " + eng16 + "\n";
    }
}
