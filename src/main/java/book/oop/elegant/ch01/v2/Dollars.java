package book.oop.elegant.ch01.v2;

public class Dollars implements Amount {

    private final String dollars;

    public Dollars(String amount) {
        this.dollars = amount;
    }

    @Override
    public int getAmount() {
        return Integer.parseInt(this.dollars);
    }
}

