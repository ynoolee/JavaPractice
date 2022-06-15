package book.oop.elegant.ch02;

public class CashV2 {
	private final ExchangeV2 exchange; // 환율정보
	private final float cents;

	public CashV2(ExchangeV2 exchange, float cents) {
		this.exchange = exchange;
		this.cents = cents;
	}
	//
	// public CashV2 in(String currency) {
	// 	return new CashV2(
	// 		this.exchange,
	// 		this.cents * this.exchange.rate(
	// 			"USD", currency
	// 		)
	// 	);
	// }

	public CashV2 in(String currency) {
		// 불변객체네
		return new CashV2(
			this.exchange, // 모든 객체를 새로 만든다는 것은 아닌 것
			this.cents * this.exchange.rate(currency)
		);
	}

	public float getCents() {
		return cents;
	}
}
