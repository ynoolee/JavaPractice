package learningJava.exception.cause;

public class ControllerException extends RuntimeException{
	public ControllerException(String message, Throwable cause) {
		super(message, cause);
	}

	public ControllerException(Throwable cause) {
		super(cause);
	}
}
