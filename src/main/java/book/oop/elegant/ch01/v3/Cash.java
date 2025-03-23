package book.oop.elegant.ch01.v3;

class Cash {

    private int amount;

    public Cash(int amount) {
        this.amount = amount;
    }

    public Cash(String amount) {
        this(Integer.parseInt(amount));
    }

}
