package eg.com.workmotion.employee.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import eg.com.workmotion.employee.dto.EmployeeDto;
import eg.com.workmotion.employee.dto.EmployeeStatusUpdateRequest;
import eg.com.workmotion.employee.dto.NewEmployeeRequestDto;
import eg.com.workmotion.employee.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/employeeApi")
@AllArgsConstructor
@Slf4j
public class EmployeeResource {
	
	
	@Autowired
	EmployeeService employeeService;

	
	@PostMapping(path = "/createEmployee", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeDto> createEmployee(@Valid @RequestBody NewEmployeeRequestDto newEmployee) {
		
		log.info("Creating Employee Name: " + newEmployee.getName()+" Contract: "+ newEmployee.getContractType()+" Birth date : "+ newEmployee.getBirthDate());
		
		
		return ResponseEntity.ok(employeeService.createEmployee(newEmployee));
	}
	
	@PostMapping(path = "/changeEmployeeState", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeDto> changeEmployeeState(
			@Valid @RequestBody EmployeeStatusUpdateRequest employeeStatusUpdateRequest) {
		
		EmployeeDto result = employeeService.changeEmployeeStatus(employeeStatusUpdateRequest);
		return ResponseEntity.ok(result);
	}
	
	//An Endpoint to fetch employee details
	@GetMapping(path = "/employee/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmployeeDto> fetchEmployeeDetails(@PathVariable Long id){
		
		return ResponseEntity.ok(employeeService.getEmployeeDetails(id));
	}
}
