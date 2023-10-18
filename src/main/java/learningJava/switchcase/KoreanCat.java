package learningJava.switchcase;

public class KoreanCat implements Cat {

    @Override
    public String meowSound() {
        return "먀";
    }

    @Override
    public CatType type() {
        return CatType.KOREAN;
    }
}
