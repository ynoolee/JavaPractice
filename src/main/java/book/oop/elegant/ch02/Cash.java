package book.oop.elegant.ch02;

public class Cash {
	private final Exchange exchange; // 환율정보
	private final float cents;

	public Cash(Exchange exchange, float cents) {
		this.exchange = exchange;
		this.cents = cents;
	}

	public Cash in(String currency) {
		// 불변객체네
		return new Cash(
			this.exchange, // 모든 객체를 새로 만든다는 것은 아닌 것
			this.cents * this.exchange.rate(
				"USD", currency
			)
		);
	}

	public float getCents() {
		return cents;
	}
}
