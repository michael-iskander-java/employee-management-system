package eg.com.workmotion.employee.dto;


import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import eg.com.workmotion.employee.helper.ContractType;
import eg.com.workmotion.employee.helper.SecurityCheckStatus;
import eg.com.workmotion.employee.helper.Status;
import eg.com.workmotion.employee.helper.WorkPermitStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
	
	private Long id;

	private String name;
	
	private ContractType contractType;
	
	private String jobTitle;

	
	private Date birthDate;
	
	private Status employeeStatus;	
	private SecurityCheckStatus employeeSecurityCheckStatus;
	private WorkPermitStatus employeeWorkPermitStatus;
}
