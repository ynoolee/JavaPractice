package book.effectiveJava.ch01.item03;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ElvisTest {

    @Test
    @DisplayName("reflection 을 통해 private 생성자를 통해 싱글톤 객체를 깨트릴 수 있다")
    public void given_defaultConstructor_whenCallConstructor_thenNewInstance() {

        try {
            Constructor<Elvis> defaultConstructor = Elvis.class.getDeclaredConstructor();
            defaultConstructor.setAccessible(true);

            Elvis elvis1 = defaultConstructor.newInstance();
            Elvis elvis2 = defaultConstructor.newInstance();

            Assertions.assertThat(elvis1)
                .isNotEqualTo(elvis2);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}