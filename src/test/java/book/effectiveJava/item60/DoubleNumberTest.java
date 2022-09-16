package book.effectiveJava.item60;

import java.math.BigDecimal;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class DoubleNumberTest {

    private final String underPrecisionDouble = "1.111111111111111";

    private final String outOfPrecisionDouble = "1.11111111111111111";

    @Nested
    public class DoubleTest {

        @Test
        @DisplayName("double 타입의 precision 을 넘어서지 않는다면 오차가 생기지 않는다")
        void test() {
            double d = Double.parseDouble(underPrecisionDouble);

            Assertions.assertThat(d + d)
                .isEqualTo(2.222222222222222);
        }

        @Test
        @DisplayName("double 타입의 precision(15~16) 을 넘어서면 오차가 생긴다")
        void test2() {
            double d = Double.parseDouble(outOfPrecisionDouble);

            Assertions.assertThat(Double.toString(d))
                .isNotEqualTo("1.11111111111111111");

            Assertions.assertThat(Double.toString(d))
                .isEqualTo("1.1111111111111112");
        }

        @Test
        @DisplayName("double 타입을 사용하느니 정수형으로 값을 변경해서 사용하는 것이 좋다")
        void convert() {
            String[] nums = outOfPrecisionDouble.split("\\.");

            long converted = Long.parseLong(nums[0]);

            int precision = nums[1].length();

            converted *= Math.pow(10, precision);
            converted += Long.parseLong(nums[1]);

            Assertions.assertThat(Long.toString(converted))
                .isEqualTo("111111111111111111");
        }
    }

    @Nested
    class BigDecimalTest {

        @Test
        @DisplayName("BigDecimal 타입을 사용할 경우 소수부가 15를 넘어서더라도 오차가 생기지 않는다")
        void testDataStructure() {

            BigDecimal bigDecimal1 = new BigDecimal(outOfPrecisionDouble);
            BigDecimal bigDecimal2 = new BigDecimal(outOfPrecisionDouble);

            bigDecimal1 = bigDecimal1.add(bigDecimal2);

            Assertions.assertThat(bigDecimal1)
                .isEqualTo(new BigDecimal("2.22222222222222222"));
        }

        @Test
        @DisplayName("생성자에 부동소수 리터럴 값을 전달할 경우, 해당 부동소수 타입 값을 생성하는 과정에서 또다시 오차가 발생한다")
        void givenDoubleLiteral() {
            BigDecimal bigDecimal = new BigDecimal(
                Double.parseDouble(outOfPrecisionDouble)); // 인텔리제이에서도 경고를 날리고 있다

            Assertions.assertThat(bigDecimal)
                .isNotEqualTo(new BigDecimal(outOfPrecisionDouble));
        }
    }

}
