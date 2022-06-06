package book.effectiveJava.item30;

public class Animal {
	private final String name;

	public Animal(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Animal{" +
			"name='" + name + '\'' +
			'}';
	}
}
