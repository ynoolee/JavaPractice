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

}