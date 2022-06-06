package book.effectiveJava.item26;

import java.util.List;
import java.util.Set;

public class AllowedClasLiteral {
	void testClassLiteral(){
		Class<List> listClass = List.class;
		// List<String>.class 불가능  - Class literal 에는 Parameterized type 을 사용할 수 없다.
		Class<String[]> aClass = String[].class; // 배열에 대해서는 허용
		Class<Integer> integerClass = int.class; // 원시타입에 대해서도 허용
	}

	void useInstanceOfToGenericType(Object set) {
		Set<?> converted = (Set<?>)set;

		if ( set instanceof Set) {
			Set<?> mySet = (Set<?>)set;
		}

	}

	public static void main(String[] args) {
		AllowedClasLiteral main = new AllowedClasLiteral();
		main.useInstanceOfToGenericType(Set.of("a","b"));
	}
}
