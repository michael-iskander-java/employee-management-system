package eg.com.workmotion.employee.service;

import eg.com.workmotion.employee.dto.EmployeeDto;
import eg.com.workmotion.employee.dto.EmployeeStatusUpdateRequest;
import eg.com.workmotion.employee.dto.NewEmployeeRequestDto;

public interface EmployeeService {
	EmployeeDto createEmployee(NewEmployeeRequestDto employee);
	EmployeeDto changeEmployeeStatus(EmployeeStatusUpdateRequest newStatus);
	EmployeeDto getEmployeeDetails(Long id);
}
