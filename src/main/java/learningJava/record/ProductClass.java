package learningJava.record;

import java.util.Objects;

public class ProductClass {
    private final String name;
    private final int quantity;

    public ProductClass(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductClass that = (ProductClass) o;
        return quantity == that.quantity && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, quantity);
    }
}
