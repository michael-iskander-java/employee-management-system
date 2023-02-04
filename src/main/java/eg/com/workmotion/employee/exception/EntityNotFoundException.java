package eg.com.workmotion.employee.exception;

public class EntityNotFoundException extends GenericException {

  private static final long serialVersionUID = 1L;

  public EntityNotFoundException(String msg) {
    super(msg);
  }

  public EntityNotFoundException(ErrorReason reason) {
    super(reason);
  }

  public EntityNotFoundException(ErrorReason errorReason, Object... reasonParams) {
    super(errorReason, reasonParams);
  }
}
