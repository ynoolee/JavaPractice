package learningJava.optional;

public class AService {

    public A get(boolean exception) {
        if (exception) {
            throw new IllegalArgumentException("a");
        } else {
            return null;
        }
    }
}
