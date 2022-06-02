package effectiveJava.item30;

import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TypeInferenceTest {
	static <T> T pick(T a1, T a2) { return a2; }

	@Test
	@DisplayName("제네릭 메소드 타입 추론 테스트")
	void test() {
		// Cloneable s = pick("d", new ArrayList<String>()); // 컴파일 에러 - 타입 추론 결과와 매칭하지 않음 : no instance(s) of type variable(s) exist so that String conforms to Cloneable
		HashMap<String, Integer> strList = new HashMap(); // X 이렇게 하지 말것!
	}


}
