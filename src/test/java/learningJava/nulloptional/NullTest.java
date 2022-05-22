package learningJava.nulloptional;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class NullTest {
	@Test
	@DisplayName("null 에 대한 Casting 이 가능하다")
	public void given_nullReferenceType_when_casting_then_success() {
		String str = (String)null;
	}

	@Nested
	@DisplayName("null 값을 가진 NullableClass 참조타입 변수를 사용하여")
	public class NullableClassInstanceTest {
		@Test
		@DisplayName("해당 타입에 정의된 정적 클래스 메소드 호출에 성공한다")
		public void given_nullReferenceType_when_invokeStaticMethod_thenSuccess() {
			NullableClass nullable = null;

			assertDoesNotThrow(() -> nullable.hello()); //  클래스 멤버에 대한 접근은 ClassName.staticMethod(); 를 통하는 것을 권장한다
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