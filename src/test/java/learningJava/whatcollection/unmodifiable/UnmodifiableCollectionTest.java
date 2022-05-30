package learningJava.whatcollection.unmodifiable;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import learningJava.whatcollection.immutable.Address;
import learningJava.whatcollection.immutable.Person;

public class UnmodifiableCollectionTest {
	@Test
	@DisplayName("UnmodifiableList 가 래핑하는 컬렉션 인스턴스에 대한 변경은 UnmodifiableList 에 visible 하다")
	public void test() {
		// given
		List<Person> people = new ArrayList<>();
		people.add(Person.of(new Address("Street", "Samsung-Building")));
		people.add(Person.of(new Address("MyStreet", "Samsung-Building")));

		List<Person> unmodifiableList = Collections.unmodifiableList(people);
		int beforeSize = unmodifiableList.size();

		// when
		people.add(Person.of(new Address("YourStreet", "Samsung-Building"))); // no exception
		int afterSize = unmodifiableList.size();

		// then
		assertThat(beforeSize).isNotEqualTo(afterSize);
	}
}
