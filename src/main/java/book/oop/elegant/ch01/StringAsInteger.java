package book.oop.elegant.ch01;

public class StringAsInteger extends Number{
	private String text;

	public StringAsInteger(String text) {
		this.text = text;
	}

	public int intValue() {
		return Integer.parseInt(text);
	}

	@Override
	public long longValue() {
		return Integer.parseInt(text);
	}

	@Override
	public float floatValue() {
		return Float.parseFloat(text);
	}

	@Override
	public double doubleValue() {
		return Double.parseDouble(text);
	}
}
