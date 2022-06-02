package effectiveJava.item30;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Animals {
	private Set<Animal> animals = new HashSet<>();

	public void union(Set<? extends Animal> animals) {
		for (Animal animal : animals) {
			this.animals.add(animal);
		}
		// animals.stream()
		// 	.map(animal -> this.animals.add(animal)); //
	}

	public void printAll(){
		System.out.println(this.animals);
	}

	/**
	 * animals 에 있는 객체(해쉬코드값!) 가 모두 존재하는지 확인한다
	 * 하나라도 존재하지 않으면 false 를 리턴한다
	 * */
	public boolean hasElements(Collection<? extends Animal> animals) {
		return !animals.stream()
			.map(animal ->
				this.animals.stream()
					.anyMatch(argAnimal -> argAnimal.equals(animal))
			).anyMatch(result -> result == false);
	}

}
