package book.oop.elegant.ch01;

public class StringAsInteger2 extends Number{
	private int num;

	public StringAsInteger2(String text) {
		this.num = Integer.parseInt(text);
	}

	public int intValue() {
		return num;
	}

	@Override
	public long longValue() {
		return 0;
	}

	@Override
	public float floatValue() {
		return 0;
	}

	@Override
	public double doubleValue() {
		return 0;
	}
}
