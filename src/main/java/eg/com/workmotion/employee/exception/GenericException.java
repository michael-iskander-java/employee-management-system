package eg.com.workmotion.employee.exception;



public class GenericException extends RuntimeException {

  private static final long serialVersionUID = 8253360261424020850L;
  public Object errorDetails;
  protected ErrorReason errorReason;


  public GenericException(String message) {
    super(message);
  }

  public GenericException(String message, Throwable cause) {
    super(message, cause);
  }

  public GenericException(ErrorReason errorReason, Object... reasonParams) {
    super((reasonParams == null || (reasonParams.length) == 0) ? errorReason.getDescription()
        : String.format(errorReason.getDescription(), reasonParams));
    this.errorReason = errorReason;
    this.errorDetails = reasonParams;
  }

  public GenericException(ErrorReason errorReason, Throwable cause) {
    super(errorReason.getDescription(), cause);
    this.errorReason = errorReason;
  }

  public GenericException(ErrorReason errorReason, Object reasonParam, Throwable cause) {
    super(String.format(errorReason.getDescription(), reasonParam), cause);
    this.errorReason = errorReason;
  }

  public GenericException(ErrorReason errorReason, Object reasonParam1, Object reasonParam2,
      Throwable cause) {
    super(String.format(errorReason.getDescription(), reasonParam1, reasonParam2), cause);
    this.errorReason = errorReason;
  }

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public ErrorReason getErrorReason() {
    return this.errorReason;
  }

  public void setErrorReason(ErrorReason errorReason) {
    this.errorReason = errorReason;
  }

  
}

