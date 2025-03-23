package book.oop.elegant.ch01.v2;

class Cash {

    private Amount amount;

    public Cash(Amount amount) {
        this.amount = amount;
    }

    public int getCash() {
        return this.amount.getAmount();
    }
}
