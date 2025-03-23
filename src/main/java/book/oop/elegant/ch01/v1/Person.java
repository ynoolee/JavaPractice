package book.oop.elegant.ch01.v1;

public class Person {
    private int cash;

    public Person(int cash) {
        this.cash = cash;
    }

    public Person(String cash) {
        this(Integer.parseInt(cash));
    }
}
