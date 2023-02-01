package learningJava.record;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductRecordTest {

    @Test
    @DisplayName("동등한 데이터로 구성된 서로 다른 두 record 객체는 동등하다")
    void givenTwoRecordObjects_whenInvokeEquals_thenTrue() {
        ProductRecord p1 = new ProductRecord("a", 10);
        ProductRecord p2 = new ProductRecord("a", 10);

        Assertions.assertThat(p1)
            .isEqualTo(p2);
    }


    @Test
    @DisplayName("동등한 데이터로 구성된 서로 다른 두 record 객체는 동일하지 않다")
    void givenTwoRecordObjects_whenInvokeEqualOperator_thenTrue() {
        ProductRecord p1 = new ProductRecord("a", 10);
        ProductRecord p2 = new ProductRecord("a", 10);

        Assertions.assertThat(p1 == p2)
            .isFalse();
    }
}