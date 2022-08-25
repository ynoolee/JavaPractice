package book.effectiveJava.ch08.item53;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class VarargsTest {

    @Nested
    @DisplayName("1개 이상의 인자를 요구하며, 가변인수만을 매개변수로 갖는 min 메소드 테스트")
    class MinTest {
        @Test
        @DisplayName("0개의 인자를 전달하여 가변인수 메소드를 호출할 경우 런타임 예외가 발생한다")
        void givenZeroArgs() {
            Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(Varargs::min);
        }
    }
}