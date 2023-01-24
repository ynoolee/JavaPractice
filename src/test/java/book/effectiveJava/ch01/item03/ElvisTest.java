package book.effectiveJava.ch01.item03;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ElvisTest {

    @Test
    @DisplayName("싱글톤 객체의 getIsntance 메소드를 Supplier 로 사용할 수 있다")
    void test() {
        Concert concert = new Concert();
        concert.start(Elvis::getInstance);
    }

}