package book.effectiveJava.ch01.item01;

public class MyFont implements Font {

    private final String fontFamily;

    private final int fontSize;

    public MyFont(String fontFamily, int fontSize) {
        this.fontFamily = fontFamily;
        this.fontSize = fontSize;
    }

    @Override
    public String getFontFamily() {
        return this.fontFamily;
    }

    @Override
    public int getFontSize() {
        return this.fontSize;
    }
}
