package book.effectiveJava.ch01.item01;

public class Character {

    private char value;

    private String color;

    private Font font;

    public Character(char value, String color, Font font) {
        this.value = value;
        this.color = color;
        this.font = font;
    }

    public Font getFont() {
        return this.font;
    }
}
