package book.oop.elegant.ch02;

public class Dollar2EuroRatio implements Ratio {
	private final float ratio;

	public Dollar2EuroRatio(float ratio) {
		this.ratio = ratio;
	}

	@Override
	public float ratio() {
		return this.ratio;
	}
}
