package learningJava.exception.cause;

public class Controller {
	private final Service service;

	public Controller(Service service) {
		this.service = service;
	}

	public void process(){
		try{
			service.get();
		} catch (Exception e) {
			throw new ControllerException("잘못된 입력입니다", e);
		}
	}
}
