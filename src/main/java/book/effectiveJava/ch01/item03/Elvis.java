package book.effectiveJava.ch01.item03;

public class Elvis {

    private static final Elvis elvis = new Elvis();

    private static boolean created;

    private Elvis() {
        if (created) {
            throw new UnsupportedOperationException("can't be created again by cosntructor");
        }

        created = true;
    }
}
