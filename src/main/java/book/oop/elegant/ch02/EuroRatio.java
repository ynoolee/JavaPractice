package book.oop.elegant.ch02;

public class EuroRatio implements Ratio {
	private final float ratio;

	public EuroRatio(float ratio) {
		this.ratio = ratio;
	}

	@Override
	public float ratio() {
		return this.ratio;
	}
}
