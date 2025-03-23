package book.oop.elegant.ch01.v3;

public class Dollars {

    private Cash cash;

    public Dollars(Cash cash) {
        this.cash = cash;
    }

    public Dollars(String amount) {
        this(new Cash(amount));
    }
}
