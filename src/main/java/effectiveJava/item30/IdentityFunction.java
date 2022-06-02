package effectiveJava.item30;

import java.util.function.UnaryOperator;

public class IdentityFunction {
	private static final UnaryOperator<Object> IDENTITY_FN = (t) -> t;

	@SuppressWarnings("unchecked")
	public static <T> UnaryOperator<T> identityFunction() {
		return (UnaryOperator<T>) IDENTITY_FN; // 비검사 형변환 경고 - 하지만 우리는 이것이 안전함을 알고있으니 경고를 숨겨주자
 	}
}
