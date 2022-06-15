package book.oop.elegant.ch02;

public interface ExchangeV2 {
	float rate(String origin, String target);
	float rate(String target);

	final class Fake implements ExchangeV2 {
		@Override
		public float rate(String origin, String target) {
			return 1.15f;
		}

		@Override
		public float rate(String target) {
			return this.rate("USD", target);
		}
	}
}
