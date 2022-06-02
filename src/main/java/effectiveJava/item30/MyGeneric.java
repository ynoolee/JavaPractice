package effectiveJava.item30;

import java.util.HashSet;
import java.util.Set;

public class MyGeneric {

	public static Set union1(Set s1, Set s2) {
		Set result = new HashSet<>(s1);
		result.addAll(s2);
		return result;
	}
	public static <E> Set<E> union2(Set<E> s1, Set<E> s2) {
		Set<E> result = new HashSet<>(s1);
		result.addAll(s2);
		return result;
	}



	public static void main(String[] args) {
		Set<String> guys = Set.of("Tom", "Dick", "Harry");
		Set<String> stooges = Set.of("Larry", "Moe", "Curly");

		Set<String> result1 = union2(guys, stooges);
		System.out.println(result1); // [Moe, Tom, Harry, Larry, Curly, Dick]

		Set<String> result2 = union1(guys, stooges);
		System.out.println(result2); // [Moe, Tom, Harry, Larry, Curly, Dick]
	}
}
