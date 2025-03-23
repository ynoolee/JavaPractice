package book.oop.elegant.ch01.v1;

class Cash {

    private int dollars;

    public Cash(String  dollars) {
        this.dollars = Integer.parseInt(dollars);
    }

    public int getAmount() {
        return this.dollars;
    }
}
