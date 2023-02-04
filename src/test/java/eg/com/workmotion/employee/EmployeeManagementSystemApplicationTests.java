package eg.com.workmotion.employee;

import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;

import eg.com.workmotion.employee.dto.EmployeeDto;
import eg.com.workmotion.employee.dto.NewEmployeeRequestDto;

import eg.com.workmotion.employee.helper.ContractType;
import eg.com.workmotion.employee.helper.Status;

import org.junit.Assert;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;

import java.net.URI;
import org.springframework.boot.test.web.server.LocalServerPort;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class EmployeeManagementSystemApplicationTests {

	@LocalServerPort
	private int randomServerPort;
	@Autowired
	private TestRestTemplate restTemplate;
	
	private Long employeeId;
	

	@Test
	public void testCreateEmployee() throws Exception {

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

}
