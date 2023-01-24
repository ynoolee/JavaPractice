package book.effectiveJava.ch01.item03;

import static org.junit.jupiter.api.Assertions.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MetaElvisTest {
    @Test
    @DisplayName("제네릭 싱글톤 팩토리를 사용하여 , 다른 타입의 동일한 인스턴스를 생성한다")
    void test1() {
        MetaElvis<String> elvis1 = MetaElvis.getInstance();
        MetaElvis<Integer> elvis2 = MetaElvis.getInstance();

        Assertions.assertThat(elvis1)
            .isEqualTo(elvis2);
    }
}