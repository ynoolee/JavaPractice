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

}