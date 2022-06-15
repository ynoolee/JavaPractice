package book.oop.elegant.ch02;

interface Exchange {
	float rate(String origin, String target);
	final class Fake implements Exchange {
		@Override
		public float rate(String origin, String target) {
			return 1.15f;
		}
	}
}
