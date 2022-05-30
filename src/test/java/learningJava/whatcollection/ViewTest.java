package learningJava.whatcollection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ViewTest {
	@Test
	@DisplayName("keySet() 을 리턴한 Map 에 대한 변경은 리턴된 view 에서 visible 하다")
	public void testVisible() {
		Map<Integer,String> map = new HashMap<>();
		map.put(1,"a");
		map.put(2,"b");
		map.put(3,"c");

		Set<Integer> mapView = map.keySet();

		// when
		map.remove(2);

		// then
		Assertions.assertThat(mapView.contains(2))
			.isFalse();
	}

	@Test
	@DisplayName("keySet() 이 리턴한 View 에 대한 변경이 Collection 에 반영됨을 확인한다")
	public void modifiacationToView() {
		Map<Integer,String> map = new HashMap<>();
		map.put(1,"a");
		map.put(2,"b");
		map.put(3,"c");

		Set<Integer> mapView = map.keySet();

		// when
		mapView.remove(2);

		// then
		Assertions.assertThat(map.containsKey(2))
			.isFalse();
	}
}
