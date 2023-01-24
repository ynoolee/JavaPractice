package book.effectiveJava.ch01.item03;

public class Elvis {

    private static final Elvis INSTANCE = new Elvis();

    private static boolean created;

    public static Elvis getInstance() {
        return INSTANCE;
    }

    private Elvis() {
    }


}
