package learningJava.whatcollection.immutable;

import java.util.Collections;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ImmutableCollectionTest {

	@Test
	@DisplayName("불변 리스트에서 꺼내온 원소의 값을 변경하면 리스트 내부 원소의 값이 변경된다")
	public void test_modify_element() {
		List<Person> immutableList = List.of(Person.of(new Address("Well-ga", "Caffeez")),
			Person.of(new Address("Something", "Caffeez")));

		Person person = immutableList.get(0);
		Address oldAddress = person.getAddress();
		Address newAddress = new Address("NEW-Street", "Caffeez");

		person.setAddress(newAddress); // 허용된다

		Assertions.assertThat(immutableList.get(0).getAddress())
			.isNotEqualTo(oldAddress);
		Assertions.assertThat(immutableList.get(0).getAddress())
			.isEqualTo(person.getAddress());
	}

	@Test
	@DisplayName("Collections.emptyList() 는 비어있는 불변 컬렉션을 리턴한다")
	public void test() {
		List<String> emptyList = Collections.emptyList();

		Assertions.assertThatThrownBy(() ->
			emptyList.add("test"))
			.isInstanceOf(Exception.class);
	}

}
