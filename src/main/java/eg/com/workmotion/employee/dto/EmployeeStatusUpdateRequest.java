package eg.com.workmotion.employee.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;

import eg.com.workmotion.employee.helper.SecurityCheckStatus;
import eg.com.workmotion.employee.helper.Status;
import eg.com.workmotion.employee.helper.WorkPermitStatus;

@Data
public class EmployeeStatusUpdateRequest {
	@NotNull
	private Long id;
	@NotNull
	private Status status;
	private SecurityCheckStatus securityCheckStatus;
	private WorkPermitStatus workPermitStatus;

}
