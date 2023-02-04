package eg.com.workmotion.employee.entity;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import eg.com.workmotion.employee.helper.ContractType;
import eg.com.workmotion.employee.helper.PostgreSQLEnumType;
import eg.com.workmotion.employee.helper.SecurityCheckStatus;
import eg.com.workmotion.employee.helper.Status;
import eg.com.workmotion.employee.helper.WorkPermitStatus;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee")
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
@Data
@Builder
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(name = "contract_type", nullable = false)
	@Type(type = "pgsql_enum")
	private ContractType contractType;

	@Column(name = "job_title", nullable = false)
	private String jobTitle;

	@Column(name = "birth_date", nullable = false)
	private Date birthDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "employee_status", nullable = false)
	@Type(type = "pgsql_enum")
	private Status employeeStatus;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "employee_security_check_status", nullable = true)
	@Type(type = "pgsql_enum")
	private SecurityCheckStatus employeeSecurityCheckStatus;
	
	
	@Enumerated(EnumType.STRING)
	@Column(name = "employee_work_permit_status", nullable = true)
	@Type(type = "pgsql_enum")
	private WorkPermitStatus employeeWorkPermitStatus;
}
