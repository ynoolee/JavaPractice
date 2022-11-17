package book.effectiveJava.ch12.item88;

import java.util.Date;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ImmutableTest {

    @Nested
    class DeserializeTest {

        private final Period existingPeriod = new Period(new Date(1000L), new Date(1500L));


        @Test
        @DisplayName("역직렬화를 통해 생성한 객체와, 이전에 존재하던 객체는 서로 다른 참조값을 갖는다")
        void givenObject_whenSerializedAndDeserialized_thenNotSameReference() {
            byte[] bytes = SerializationUtil.serialize(existingPeriod);

            Period deserializedPeriod = (Period) SerializationUtil.deserialize(bytes);

            Assertions.assertThat(deserializedPeriod)
                .isNotEqualTo(existingPeriod);
        }

        @Test
        @DisplayName("직렬화를 통해 바이트 스트림을 생성한 기존의 객체가 지닌 필드의 참조값과, 역직렬화를 통해 생성한 객체가 지닌 필드의 참조값은 동일하다")
        void givenObject_whenSerializedAndDeserilized_thenSameReferenceField() {
            byte[] bytes = SerializationUtil.serialize(existingPeriod);

            Period deserializedPeriod = (Period) SerializationUtil.deserialize(bytes);

            Assertions.assertThat(deserializedPeriod.endCode())
                .isEqualTo(existingPeriod.endCode());
        }
    }

}
