package learningJava.nulloptional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class OptionalTest {

    private static class Check {

        private String internalValue;

        private Check(final String isWorked) {
            this.internalValue = isWorked;
        }

        public String changeInternalValueTo(String changed) {
            internalValue = changed;

            return internalValue;
        }

        public String getInternalValue() {
            return internalValue;
        }
    }

    @Test
    @DisplayName("Optional.of() 을 인자 null 로 호출 할 경우 NPE 를 던진다")
    void test() {
        Assertions.assertThatNullPointerException()
            .isThrownBy(() -> Optional.of(null));
    }

    @Nested
    @DisplayName("Optional 내부 객체가 null 이 아닌 경우 ")
    class NotNullOptional {

        private Optional<String> notNullOptional = Optional.ofNullable("notNull");

        @DisplayName("OrElse 에 인자로 전달한 람다가 동작한다")
        @Test
        void test1() {
            // given
            var checker = new Check("original");

            // when
            var other = notNullOptional.orElse(checker.changeInternalValueTo("notOriginal"));

            // then
            Assertions.assertThat(checker.getInternalValue())
                .isEqualTo("notOriginal");
        }

        @DisplayName("OrElseGet 에 인자로 전달한 람다가 동작하지 않는다")
        @Test
        void test3() {
            // given
            var checker = new Check("original");

            // when
            var other = notNullOptional.orElseGet(
                () -> checker.changeInternalValueTo("notOriginal"));

            // then
            Assertions.assertThat(checker.getInternalValue())
                .isEqualTo("original");
        }

        @DisplayName("OrElse 에 인자로 전달되는 T 객체를 반환하지 않는다")
        @Test
        void test2() {
            var other = notNullOptional.orElse("other");

            Assertions.assertThat(other)
                .isNotEqualTo("other");
        }

        @DisplayName("OrElseGet 에 인자로 전달되는 T 객체를 반환하지 않는다")
        @Test
        void test4() {
            var other = notNullOptional.orElseGet(() -> "other");

            Assertions.assertThat(other)
                .isNotEqualTo("other");
        }
    }

    @Test
    @DisplayName("null 에 대한 Casting 이 가능하다")
    public void given_nullReferenceType_when_casting_then_success() {
        String str = (String) null;
    }

    @Nested
    @DisplayName("null 값을 가진 NullableClass 참조타입 변수를 사용하여")
    public class NullableClassInstanceTest {

        @Test
        @DisplayName("해당 타입에 정의된 정적 클래스 메소드 호출에 성공한다")
        public void given_nullReferenceType_when_invokeStaticMethod_thenSuccess() {
            NullableClass nullable = null;

            assertDoesNotThrow(
                () -> nullable.hello()); //  클래스 멤버에 대한 접근은 ClassName.staticMethod(); 를 통하는 것을 권장한다
        }
    }

    @Nested
    @DisplayName("null 값을 가진 Integer 참조타입 변수를 사용하여")
    public class NullIntegerInstanceTest {

        private final Integer i1 = null;

        @Test
        @DisplayName("논리 연산 결과 NPE 가 발생한다")
        public void given_nullReferenceType_when_doOperationUsingOperator_thenNPE() {
            Integer i2 = 1;

            Assertions.assertThatThrownBy(() -> {
                    boolean test = i1 > i2;
                })
                .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("instanceOf 결과 false 를 리턴한다")
        public void instanceOfReturnFalse() {
            Assertions.assertThat(i1 instanceof Integer).isFalse();
        }

        @Test
        @DisplayName("언박싱이 일어나면 NPE 가 발생한다")
        public void given_nullWrapperClass_when_tryToUnboxing_then_NPE() {
            // int test = myInteger; //    컴파일 오류는 없다 INVOKEVIRTUAL java/lang/Integer.intValue ()I
            Assertions.assertThatThrownBy(() ->
                    i1.intValue())
                .isInstanceOf(NullPointerException.class);
        }
    }
}
