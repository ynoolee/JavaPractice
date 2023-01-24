package book.effectiveJava.ch01.item03;

import java.io.Serializable;

public class Elvis implements Serializable {

    public static final Elvis INSTANCE = new Elvis();

    private static boolean created;

    private Elvis() {
//        if (created) {
//            throw new UnsupportedOperationException("can't be created again by cosntructor");
//        }
//
//        created = true;
    }
}
