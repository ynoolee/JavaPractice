package book.effectiveJava.ch01.item03;

public class Elvis implements Singer{

    private static final Elvis INSTANCE = new Elvis();

    private static boolean created;

    public static Elvis getInstance() {
        return INSTANCE;
    }

    private Elvis() {
    }


    @Override
    public void sing() {
        System.out.println("hello~~");
    }
}
