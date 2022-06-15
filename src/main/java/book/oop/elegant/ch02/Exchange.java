package book.oop.elegant.ch02;

interface Exchange {
	float rate(String origin, String target);

	final class Fake implements Exchange {
		@Override
		public float rate(String origin, String target) {
			return 1.15f;
		}
	}

	final class FakeV2 implements Exchange {
		private final Ratio ratio;

		public FakeV2(Ratio ratio) {
			this.ratio = ratio;
		}

		@Override
		public float rate(String origin, String target) {
			return ratio.ratio();
		}

	}
}
