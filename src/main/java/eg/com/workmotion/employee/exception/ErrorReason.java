package eg.com.workmotion.employee.exception;

public enum ErrorReason {


	EMPLOYEE_NOT_FOUND("Employee is not found."),
	EMPLOYEE_IS_NOT_IN_VALID_STATES("Employee is not in valid states."),
	EMPLOYEE_STATES_CANNOT_BE_CHANGED("Employee states cannot be changed.");
  
  private String description;

  ErrorReason(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

}
