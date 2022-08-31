package book.effectiveJava.item58;

import book.effectiveJava.item58.AdvancedFor.OUTER;
import book.effectiveJava.item58.AdvancedFor.Pair;
import java.util.List;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AdvancedForTest {
    private final AdvancedFor forClass = new AdvancedFor();

    @Test
    @DisplayName("생성된 Collection 에서 조건에 만족하는 원소들을 제거한다")
    void removeByTest() {
        List<Pair> pairs = forClass.exceedSizeV3();

        Assertions.assertThat(pairs.size())
                .isEqualTo(6);

        forClass.removeBy(
            pairs,
            pair -> pair.getOuter() == OUTER.A
        );

        Assertions.assertThat(pairs.size())
            .isEqualTo(3);
    }

    @Nested
    @DisplayName("원시타입에 대해 index 접근 및 for each 접근 속도 테스트")
    class PrimitiveForTest {
        private int[] ints;
        private int sum;

        @BeforeEach
        void setUp() {
            ints = IntStream.range(0,100000000).toArray();
            sum = 0;
        }
        @Test
        @DisplayName("index 로 전체 원소들을 Iterate 하는데 걸리는 시간")
        void iterateV2() {
            for (int idx = 0; idx < ints.length; idx++) {
                sum += ints[idx];
            }
        }

        @Test
        @DisplayName("for each 로 전체 원소들을 iterate 하는데 걸리는 시간")
        void iterateV1() {
            for (int numb : ints) {
                sum += numb;
            }
        }
    }
}