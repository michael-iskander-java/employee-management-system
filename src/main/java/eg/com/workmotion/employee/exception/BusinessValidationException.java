package eg.com.workmotion.employee.exception;

public class BusinessValidationException extends GenericException {

	private static final long serialVersionUID = 1L;

	private String message;

	public BusinessValidationException(String message) {
		super(message);
		this.message = message;
	}

	public BusinessValidationException(ErrorReason message) {
		super(message);
		this.message = message.getDescription();
	}

	public String getMessage() {
		return message;
	}
}
