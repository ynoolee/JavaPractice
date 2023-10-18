package learningJava.switchcase;

public class RussianBlue implements Cat{

    @Override
    public String meowSound() {
        return "냐아";
    }

    @Override
    public CatType type() {
        return CatType.RUSSIAN_BLUE;
    }
}
