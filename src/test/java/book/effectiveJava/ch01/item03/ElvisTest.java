package book.effectiveJava.ch01.item03;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ElvisTest {

    @Test
    @DisplayName("reflection 을 통해 private 생성자를 직접 호출 할 경우, 싱글톤 객체에서는 예외를 발생한다")
    public void given_defaultConstructor_whenCallConstructor_thenNewInstance() {

        try {
            Constructor<Elvis> defaultConstructor = Elvis.class.getDeclaredConstructor();
            defaultConstructor.setAccessible(true);

            Assertions.assertThatExceptionOfType(InvocationTargetException.class)
                .isThrownBy(defaultConstructor::newInstance)
                .withCauseExactlyInstanceOf(UnsupportedOperationException.class);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

}