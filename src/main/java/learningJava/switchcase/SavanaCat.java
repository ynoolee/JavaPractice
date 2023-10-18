package learningJava.switchcase;

public class SavanaCat implements Cat {

    @Override
    public String meowSound() {
        return "캭";
    }

    @Override
    public CatType type() {
        return CatType.SAVANA;
    }
}
