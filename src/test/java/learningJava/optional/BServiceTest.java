package learningJava.optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BServiceTest {

    private final BService bService = new BService(new AService());

    @Test
    @DisplayName("F 의존하는 서비스에서 런타임 예외를 던진 경우 orElseThrow 를 실행하지 못하고 종료된다")
    public void givenExceptionThenFail() {
        Assertions.assertThatThrownBy(() -> bService.process(true))
            .hasMessage("a");
    }

    @Test
    @DisplayName("S 의존하는 서비스에서 null 을 반환할 경우 orElseThrow 를 실행한다")
    public void givenNullThenSuccess() {
        Assertions.assertThatThrownBy(() -> bService.process(false))
            .hasMessage("Bservice");
    }
}