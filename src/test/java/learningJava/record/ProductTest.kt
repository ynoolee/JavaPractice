package learningJava.record

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.*

class ProductTest {

    @Test
    fun `동등한 데이터로 구성된 서로 다른 두 데이터 객체는 동등하다`() {
        val p1 = Product("a", 10)
        val p2 = Product("a", 10)

        Assertions.assertThat(p1 == p2).isTrue()
    }

    @Test
    fun `동등한 데이터로 구성된 서로 다른 두 데이터 객체는 동일하지 않다`() {
        val p1 = Product("a", 10)
        val p2 = Product("a", 10)

        println(p1)
        println(p2)
        Assertions.assertThat(p1 === p2).isFalse()
    }


}

