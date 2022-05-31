package learningJava.exception.cause;

public class ServiceException extends RuntimeException{
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String message) {
		super(message);
	}
}
