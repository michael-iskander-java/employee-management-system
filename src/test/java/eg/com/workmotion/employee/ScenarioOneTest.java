package eg.com.workmotion.employee;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import eg.com.workmotion.employee.dto.EmployeeDto;
import eg.com.workmotion.employee.dto.EmployeeStatusUpdateRequest;
import eg.com.workmotion.employee.dto.NewEmployeeRequestDto;
import eg.com.workmotion.employee.helper.ContractType;
import eg.com.workmotion.employee.helper.SecurityCheckStatus;
import eg.com.workmotion.employee.helper.Status;
import eg.com.workmotion.employee.helper.WorkPermitStatus;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ScenarioOneTest {

	@LocalServerPort
	private int randomServerPort;
	@Autowired
	private TestRestTemplate restTemplate;
	
	private static Long employeeId;
	//create an employee
	@Test
	public void testA() throws Exception {

		Date date = new SimpleDateFormat("MM/dd/yyyy").parse("08/31/1980");
		NewEmployeeRequestDto employee = new NewEmployeeRequestDto();
		employee.setName("Michael Iskander");
		employee.setContractType(ContractType.TEMPORARY);
		employee.setJobTitle("Software Specialist");
		employee.setBirthDate(date);
		
		final String baseUrl = "http://localhost:" + randomServerPort + "/employeeApi";
		URI uri = new URI(baseUrl + "/createEmployee");

		HttpEntity<NewEmployeeRequestDto> request = new HttpEntity<>(employee);

		ResponseEntity<EmployeeDto> result = this.restTemplate.postForEntity(uri, request, EmployeeDto.class);

		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertNotNull(result.getBody());
		Assert.assertEquals(result.getBody().getEmployeeStatus(), Status.ADDED);
		employeeId = result.getBody().getId();
		
	}
	//Update state of the employee to `IN-CHECK`
	@Test
	public void testB() throws Exception {
		
		EmployeeStatusUpdateRequest stateUpdate = new EmployeeStatusUpdateRequest();
		stateUpdate.setId(employeeId);
		stateUpdate.setStatus(Status.IN_CHECK);
		
		final String baseUrl = "http://localhost:" + randomServerPort + "/employeeApi";
		URI uri = new URI(baseUrl + "/changeEmployeeState");
		
		HttpEntity<EmployeeStatusUpdateRequest> request = new HttpEntity<>(stateUpdate);
		
		ResponseEntity<EmployeeDto> result = this.restTemplate.postForEntity(uri, request, EmployeeDto.class);
		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertNotNull(result.getBody());
		Assert.assertEquals(result.getBody().getEmployeeStatus(), Status.IN_CHECK);
		Assert.assertEquals(result.getBody().getEmployeeSecurityCheckStatus(), SecurityCheckStatus.SECURITY_CHECK_STARTED);
		Assert.assertEquals(result.getBody().getEmployeeWorkPermitStatus(), WorkPermitStatus.WORK_PERMIT_CHECK_STARTED);
		
		
	}
	
	//Update substate of `IN-CHECK` state of the employee to `SECURITY_CHECK_FINISHED`
	
	@Test
	public void testC() throws Exception {
		
		EmployeeStatusUpdateRequest stateUpdate = new EmployeeStatusUpdateRequest();
		stateUpdate.setId(employeeId);
		stateUpdate.setStatus(Status.IN_CHECK);
		stateUpdate.setSecurityCheckStatus(SecurityCheckStatus.SECURITY_CHECK_FINISHED);
		
		final String baseUrl = "http://localhost:" + randomServerPort + "/employeeApi";
		URI uri = new URI(baseUrl + "/changeEmployeeState");
		
		HttpEntity<EmployeeStatusUpdateRequest> request = new HttpEntity<>(stateUpdate);
		
		ResponseEntity<EmployeeDto> result = this.restTemplate.postForEntity(uri, request, EmployeeDto.class);
		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertNotNull(result.getBody());
		Assert.assertEquals(result.getBody().getEmployeeStatus(), Status.IN_CHECK);
		Assert.assertEquals(result.getBody().getEmployeeSecurityCheckStatus(), SecurityCheckStatus.SECURITY_CHECK_FINISHED);
		Assert.assertEquals(result.getBody().getEmployeeWorkPermitStatus(), WorkPermitStatus.WORK_PERMIT_CHECK_STARTED);
		
		
	}
	
	//Update substate of `IN-CHECK` state of the employee to `WORK_PERMIT_CHECK_PENDING_VERIFICATION`
	@Test
	public void testD() throws Exception {
		
		EmployeeStatusUpdateRequest stateUpdate = new EmployeeStatusUpdateRequest();
		stateUpdate.setId(employeeId);
		stateUpdate.setStatus(Status.IN_CHECK);
		stateUpdate.setWorkPermitStatus(WorkPermitStatus.WORK_PERMIT_CHECK_PENDING_VERIFICATION);
		
		final String baseUrl = "http://localhost:" + randomServerPort + "/employeeApi";
		URI uri = new URI(baseUrl + "/changeEmployeeState");
		
		HttpEntity<EmployeeStatusUpdateRequest> request = new HttpEntity<>(stateUpdate);
		
		ResponseEntity<EmployeeDto> result = this.restTemplate.postForEntity(uri, request, EmployeeDto.class);
		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertNotNull(result.getBody());
		Assert.assertEquals(result.getBody().getEmployeeStatus(), Status.IN_CHECK);
		Assert.assertEquals(result.getBody().getEmployeeSecurityCheckStatus(), SecurityCheckStatus.SECURITY_CHECK_FINISHED);
		Assert.assertEquals(result.getBody().getEmployeeWorkPermitStatus(), WorkPermitStatus.WORK_PERMIT_CHECK_PENDING_VERIFICATION);
		
		
	}
	
	//Update substate of `IN-CHECK` state the employee to `WORK_PERMIT_CHECK_FINISHED` (employee is auto-transitioned to `APPROVED` state)

	@Test
	public void testE() throws Exception {
		
		EmployeeStatusUpdateRequest stateUpdate = new EmployeeStatusUpdateRequest();
		stateUpdate.setId(employeeId);
		stateUpdate.setStatus(Status.IN_CHECK);
		stateUpdate.setWorkPermitStatus(WorkPermitStatus.WORK_PERMIT_CHECK_FINISHED);
		
		final String baseUrl = "http://localhost:" + randomServerPort + "/employeeApi";
		URI uri = new URI(baseUrl + "/changeEmployeeState");
		
		HttpEntity<EmployeeStatusUpdateRequest> request = new HttpEntity<>(stateUpdate);
		
		ResponseEntity<EmployeeDto> result = this.restTemplate.postForEntity(uri, request, EmployeeDto.class);
		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertNotNull(result.getBody());
		Assert.assertEquals(result.getBody().getEmployeeStatus(), Status.APPROVED);
		Assert.assertNull(result.getBody().getEmployeeSecurityCheckStatus());
		Assert.assertNull(result.getBody().getEmployeeWorkPermitStatus());
		
		
	}
	
	//Update state of the employee to `ACTIVE`
	
	@Test
	public void testF() throws Exception {
		
		EmployeeStatusUpdateRequest stateUpdate = new EmployeeStatusUpdateRequest();
		stateUpdate.setId(employeeId);
		stateUpdate.setStatus(Status.ACTIVE);
		
		final String baseUrl = "http://localhost:" + randomServerPort + "/employeeApi";
		URI uri = new URI(baseUrl + "/changeEmployeeState");
		
		HttpEntity<EmployeeStatusUpdateRequest> request = new HttpEntity<>(stateUpdate);
		
		ResponseEntity<EmployeeDto> result = this.restTemplate.postForEntity(uri, request, EmployeeDto.class);
		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertNotNull(result.getBody());
		Assert.assertEquals(result.getBody().getEmployeeStatus(), Status.ACTIVE);
		Assert.assertNull(result.getBody().getEmployeeSecurityCheckStatus());
		Assert.assertNull(result.getBody().getEmployeeWorkPermitStatus());
		
		
	}
}
