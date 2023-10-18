package learningJava.switchcase;

public interface Cat {
    String meowSound();

    CatType type();

    enum CatType {
        SAVANA, KOREAN, RUSSIAN_BLUE
    }
}
