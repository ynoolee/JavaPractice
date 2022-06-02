package effectiveJava.item30;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MyGenericTest {
	@Test
	@DisplayName("raw type 을 받는 유니온 메소드와 parameterized type 을 받는 유니온 메소드는 동일한 결과물을 만든다")
	void test(){
		Set<String> guys = Set.of("Tom", "Dick", "Harry");
		Set<String> stooges = Set.of("Larry", "Moe", "Curly");

		Set set = MyGeneric.union1(guys, stooges);
		Set<String> set2 = MyGeneric.union2(guys, stooges);

		Assertions.assertThat(set).hasSameElementsAs(set2);
	}

	@Test
	@DisplayName("한정적 와일드 타입 제네릭 메소드를 통해 A 의 하위타입 B,C 에 대한 Set<B> Set<C> 로부터 원소들을 가져와 합친 결과물을 만들 수 있다 ")
	void test_otherType() {
		// Animals animals = new Animals();

		Set<Carnivore> carnivores = Set.of(new Carnivore("Lion"), new Carnivore("Tiger"));
		Set<Herbivore> herbivores = Set.of(new Herbivore("코끼맄"), new Herbivore("토끼"));

		Set<Animal> animals = Animals.union(carnivores, herbivores);

		Assertions.assertThat(
			Animals.hasElementsOfOther(animals, carnivores))
			.isTrue();

		Assertions.assertThat(
			Animals.hasElementsOfOther(animals, herbivores))
			.isTrue();
		// animals.union2(carnivores);
		// animals.printAll(); // [Animal{name='Lion'}, Animal{name='Tiger'}]
		//
		//
		// animals.union2(herbivores);
		// animals.printAll(); // [Animal{name='코끼맄'}, Animal{name='Lion'}, Animal{name='토끼'}, Animal{name='Tiger'}]
		//
		// Assertions.assertThat(animals.hasElements(carnivores)).isTrue();
		// Assertions.assertThat(animals.hasElements(herbivores)).isTrue();
	}

	@Nested
	@DisplayName("Generic singleton factory 학습")
	public class GenericSingletonFactory {
		@Test
		@DisplayName("")
		void have_to_convert_type() {

		}

		@Test
		@DisplayName("타입 세이프한 emptySet 을 생성한다")
		void test_typeSafe() {
			Set<Carnivore> set = Collections.emptySet();

			set.add(new Carnivore("라이언"));
			// set.add(new Herbivore("토끼")); 컴파일 에러
		}

		@Test
		@DisplayName("String 타입 reverseComparator 를 사용하여 반대방향으로 정렬할 수 있다")
		void test_reverseOrder() {
			List<String> cyclopedia = new ArrayList<>();
			cyclopedia.add("apple");
			cyclopedia.add("abap");
			cyclopedia.add("kilometer");
			cyclopedia.add("case");

			Collections.sort(cyclopedia);

			String firstElementOfOrderedCyclopedia = cyclopedia.get(0);

			int lastIndex = cyclopedia.size()-1;
			Comparator<String> reverseOrderComparator = Collections.reverseOrder();

			Collections.sort(cyclopedia, reverseOrderComparator);

			String firstElementOfReverseOrderedCyclopedia = cyclopedia.get(lastIndex);

			Assertions.assertThat(firstElementOfReverseOrderedCyclopedia)
				.isEqualTo(firstElementOfOrderedCyclopedia);
		}

		@Test
		@DisplayName("커스텀 항등함수")
		void test_identity() {
			UnaryOperator<String> objectUnaryOperator = IdentityFunction.identityFunction();
		}
	}


}