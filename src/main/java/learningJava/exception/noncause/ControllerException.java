package learningJava.exception.noncause;

public class ControllerException extends RuntimeException{
	public ControllerException() {
	}

	public ControllerException(String message, Throwable cause) {
		super(message, cause);
	}

	public ControllerException(Throwable cause) {
		super(cause);
	}
}
