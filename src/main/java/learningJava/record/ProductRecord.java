package learningJava.record;

public record ProductRecord(String name, int quantity) {

    private static int UNKNOWN_QUANTITY = 0;

    // Non-canonical record constructor must delegate to another constructor
    public ProductRecord(ProductRecord product) {
        this(product.name(), product.quantity());
    }

    /**
     * Canonical constructor 와 Compact constructor 중 하나만 존재할 수 있다
     * */
    // Canonical record constructor - 생략 가능
    public ProductRecord(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

//    // Compact constructor
//    public Product {
//        name = "NONE";
//        quantity = UNKNOWN_QUANTITY;
//    }



    // Not Allowed form of constructor
    /*
	public Product() {

	}
	*/
}
