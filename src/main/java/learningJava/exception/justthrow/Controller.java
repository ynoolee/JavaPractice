package learningJava.exception.justthrow;

public class Controller {
	private final Service service;

	public Controller(Service service) {
		this.service = service;
	}

	public void process(){
		service.get();
	}
}
