package eg.com.workmotion.employee.dto;


import lombok.Data;


import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import eg.com.workmotion.employee.helper.ContractType;

@Data
public class NewEmployeeRequestDto {
	@NotBlank
	private String name;
	@NotNull
	private ContractType contractType;
	@NotBlank
	private String jobTitle;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@JsonFormat(pattern = "MM/dd/yyyy")
	private Date birthDate;
}
