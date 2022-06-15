package book.oop.elegant.ch02;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CashTest {

	@Test
	@DisplayName("Mock 을 사용할 때 테스트 코드는 테스트 타겟 클래스의 내부 동작에 의존한다")
	public void test() {
		Exchange exchange = Mockito.mock(Exchange.class);
		Mockito.doReturn(1.15f)
			.when(exchange)
			.rate("USD", "EUR");

		Cash dollar = new Cash(exchange, 500);
		Cash euro = dollar.in("EUR");

		Assertions.assertThat(euro.getCents())
			.isEqualTo(575f);
	}
	@Test
	@DisplayName("Mock 을 사용할 때 테스트 타겟 클래스의 내부 동작이 변경될 경우, 이 테스트는 실패한다")
	public void testV2() {
		ExchangeV2 exchange = Mockito.mock(ExchangeV2.class);
		Mockito.doReturn(1.15f)
			.when(exchange)
			.rate("USD", "EUR");

		CashV2 dollar = new CashV2(exchange, 500);
		CashV2 euro = dollar.in("EUR");

		Assertions.assertThat(euro.getCents())
			.isEqualTo(575f);
	}

	@Test
	@DisplayName("Fake 객체를 사용한 테스트는 깨지지 않는다")
	public void notBreakableTestUsingFake() {
		ExchangeV2 exchange = new ExchangeV2.Fake();

		CashV2 dollar = new CashV2(exchange, 500);
		CashV2 euro = dollar.in("EUR");

		Assertions.assertThat(euro.getCents())
			.isEqualTo(575f);
	}


	@Test
	@DisplayName("Fake 객체를 사용한 테스트")
	public void testUsingFake() {
		Exchange exchange = new Exchange.Fake();

		Cash dollar = new Cash(exchange, 500);
		Cash euro = dollar.in("EUR");

		// 이 때 드는 의문점 -> 결과가 575f 인 거는 어떻게 알아? 이 값은 어디서 나온거야??
		// -> Fake 를 좀 더 복잡하게 만들어야 한다. 상수 대신, 캡슐화된 비율을 반환하도록
		Assertions.assertThat(euro.getCents())
			.isEqualTo(575f);
	}

	@Test
	@DisplayName("환율을 주입받은 Fake 객체를 사용한 테스트")
	public void testUsingRatio() {
		Ratio dollar2EuroRatio = new Dollar2EuroRatio(1.15f);
		Exchange exchange = new Exchange.FakeV2(dollar2EuroRatio);

		Cash dollar = new Cash(exchange, 500);
		Cash euro = dollar.in("EUR");

		// FIXME : 의문점!!
		Assertions.assertThat(euro.getCents())
			.isEqualTo(dollar2EuroRatio.ratio() * 500); // 이것 역시 Cash 내부에서 구현이 어떻게 되어있는지 알아서 이렇게 작성되는 것이라는 단점...아닌가????
		// 근데 이 경우는, Cash 의 in() 기능을 테스트하려면, 그 계산 결과 값을 알아야만 테스트가 이루어질 수 밖에 없으니 어쩔 수 없는거 아닌가 ??
	}



}