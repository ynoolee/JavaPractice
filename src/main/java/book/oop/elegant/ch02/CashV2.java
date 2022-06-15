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

	// 내부구현 변경시 -> 기존의 Mock 을 사용하던 Cash 클래스에 대한 단위 테스트는 실패한다
	public CashV2 in(String currency) {
		// 불변객체네
		return new CashV2(
			this.exchange, // 모든 객체를 새로 만든다는 것은 아닌 것
			this.cents * this.exchange.rate(
				currency
			)
		);
	}

	public float getCents() {
		return cents;
	}
}
