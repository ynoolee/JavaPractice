package learningJava.whatcollection.immutable;

public class Person {
	private Address address;

	private Person() {
	}

	private Person(Address address) {
		this.address = address;
	}

	public static Person of(Address address) {
		return new Person(address);
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
