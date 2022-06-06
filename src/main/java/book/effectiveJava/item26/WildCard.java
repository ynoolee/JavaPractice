package book.effectiveJava.item26;

import java.util.Set;

public class WildCard {

	public void getWildcardCollection(Set<?> collection){
		// read 는 정상적으로 수행된다
		for (Object o : collection) {
			System.out.println(o);
		}

		collection.add(null);	// null 외에는 추가할 수 없다
		// collection.add(Integer.valueOf(10)); // 컴파일 에러

	}
	public void getCollection(Set collection){
		// read 는 정상적으로 수행된다
		for (Object o : collection) {
			System.out.println(o);
		}
	}

	public static void main(String[] args) {
		WildCard wildCard = new WildCard();

		Set<?> set = Set.of("a",1);


		wildCard.getCollection(set);
		wildCard.getWildcardCollection(set);

	}
}
