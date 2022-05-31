package learningJava.exception.noncause;

public class Controller {
	private final Service service;

	public Controller(Service service) {
		this.service = service;
	}

	public void process(){
		try{
			service.get();
		} catch (Exception e) {
			throw new ControllerException();
		}
	}
}
