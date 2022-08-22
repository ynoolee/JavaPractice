package learningJava.optional;

import java.util.Optional;

public class BService {
	private final AService aService;

	public BService(AService aService) {
		this.aService = aService;
	}

	public void process(boolean exception) {
		Optional.ofNullable(aService.get(exception))
			.orElseThrow(() -> 
				new IllegalArgumentException("Bservice")
		);
	}
}
