package eg.com.workmotion.employee.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eg.com.workmotion.employee.dto.EmployeeDto;
import eg.com.workmotion.employee.dto.EmployeeStatusUpdateRequest;
import eg.com.workmotion.employee.dto.NewEmployeeRequestDto;
import eg.com.workmotion.employee.entity.Employee;
import eg.com.workmotion.employee.exception.BusinessValidationException;
import eg.com.workmotion.employee.exception.EntityNotFoundException;
import eg.com.workmotion.employee.exception.ErrorReason;
import eg.com.workmotion.employee.helper.SecurityCheckStatus;
import eg.com.workmotion.employee.helper.Status;
import eg.com.workmotion.employee.helper.WorkPermitStatus;
import eg.com.workmotion.employee.repository.EmployeeRepository;
import eg.com.workmotion.employee.service.EmployeeService;
import org.modelmapper.ModelMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;
	
	
	@Override
	@Transactional
	public EmployeeDto createEmployee(NewEmployeeRequestDto employee) {
		
		
		ModelMapper modelMapper = new ModelMapper();
		Employee persistedEmployee = modelMapper.map(employee, Employee.class);
		persistedEmployee.setEmployeeStatus(Status.ADDED);
		persistedEmployee.setEmployeeSecurityCheckStatus(null);
		persistedEmployee.setEmployeeWorkPermitStatus(null);
		
		persistedEmployee = employeeRepository.save(persistedEmployee);
		
		log.info("Employee created: " + persistedEmployee.getId());
		
		EmployeeDto result = modelMapper.map(persistedEmployee, EmployeeDto.class);
		
		return result;
	}


	@Override
	@Transactional
	public EmployeeDto changeEmployeeStatus(EmployeeStatusUpdateRequest newStatus) {
		
		Employee persistedEmployee = employeeRepository.findById(newStatus.getId()).orElseThrow(() -> new EntityNotFoundException(ErrorReason.EMPLOYEE_NOT_FOUND+" with id: " +newStatus.getId()));
		
		log.info("Changing employee status: "+ persistedEmployee.getId());
		
		if (persistedEmployee.getEmployeeStatus()==null) {
			throw new BusinessValidationException("Employee satatus cannot be null.");
		}
		
		
		
		//new statuses
		Status newEmployeeStatus = newStatus.getStatus();
		SecurityCheckStatus newEmployeeSecurityCheckStatus = newStatus.getSecurityCheckStatus();
		WorkPermitStatus newEmployeeWorkPermitStatus = newStatus.getWorkPermitStatus();
		
		log.info("New statuses: "+ newEmployeeStatus+" "+newEmployeeSecurityCheckStatus+" "+newEmployeeWorkPermitStatus);
		//old statuses
		
		Status oldEmployeeStatus = persistedEmployee.getEmployeeStatus();
		SecurityCheckStatus oldEmployeeSecurityCheckStatus = persistedEmployee.getEmployeeSecurityCheckStatus();
		WorkPermitStatus oldEmployeeWorkPermitStatus = persistedEmployee.getEmployeeWorkPermitStatus();
		
		log.info("Old statuses: "+ oldEmployeeStatus+" "+oldEmployeeSecurityCheckStatus+" "+oldEmployeeWorkPermitStatus);
		switch (newStatus.getStatus()) {
		case ADDED:
			throw new BusinessValidationException(Status.ADDED+" status is not allowed in changing employee status.");
		case IN_CHECK:
			
			if (newEmployeeSecurityCheckStatus==null && newEmployeeWorkPermitStatus==null) {
				
				if (oldEmployeeSecurityCheckStatus == null && oldEmployeeWorkPermitStatus == null) {
					persistedEmployee.setEmployeeSecurityCheckStatus(SecurityCheckStatus.SECURITY_CHECK_STARTED);
					persistedEmployee.setEmployeeWorkPermitStatus(WorkPermitStatus.WORK_PERMIT_CHECK_STARTED);
				}
				
						
				
			}else if (newEmployeeSecurityCheckStatus==null) {
				
				if (oldEmployeeWorkPermitStatus!=null && oldEmployeeSecurityCheckStatus!=null) {
					if(oldEmployeeWorkPermitStatus.canChangeTo(newEmployeeWorkPermitStatus))
						persistedEmployee.setEmployeeWorkPermitStatus(newEmployeeWorkPermitStatus);
					else 
						throw new BusinessValidationException(oldEmployeeWorkPermitStatus + " cannot be changed to "+newEmployeeWorkPermitStatus);
				}else {
					throw new BusinessValidationException(ErrorReason.EMPLOYEE_IS_NOT_IN_VALID_STATES);
				}
			} else if  (newEmployeeWorkPermitStatus==null) {
				if (oldEmployeeWorkPermitStatus!=null && oldEmployeeSecurityCheckStatus!=null) {
					if(oldEmployeeSecurityCheckStatus.canChangeTo(newEmployeeSecurityCheckStatus)) 
						persistedEmployee.setEmployeeSecurityCheckStatus(newEmployeeSecurityCheckStatus);
					else 
						throw new BusinessValidationException(oldEmployeeSecurityCheckStatus + " cannot be changed to "+newEmployeeSecurityCheckStatus);
							
				}else {
					throw new BusinessValidationException(ErrorReason.EMPLOYEE_IS_NOT_IN_VALID_STATES);
				}
				
			}else {
				if (oldEmployeeSecurityCheckStatus.canChangeTo(newEmployeeSecurityCheckStatus) && oldEmployeeWorkPermitStatus.canChangeTo(newEmployeeWorkPermitStatus)) {
					persistedEmployee.setEmployeeSecurityCheckStatus(newEmployeeSecurityCheckStatus);
					persistedEmployee.setEmployeeWorkPermitStatus(newEmployeeWorkPermitStatus);
				} else {
					throw new BusinessValidationException(ErrorReason.EMPLOYEE_STATES_CANNOT_BE_CHANGED);
				}
				
			}
			break;
		case APPROVED:
			throw new BusinessValidationException(Status.ADDED+" status is not allowed in changing employee status.");
		case ACTIVE:	
			if (!(oldEmployeeWorkPermitStatus==null && oldEmployeeSecurityCheckStatus==null)) {
				throw new BusinessValidationException(ErrorReason.EMPLOYEE_IS_NOT_IN_VALID_STATES);
			}
		default:
			break;
		};
		
		if (oldEmployeeStatus.canChangeTo(newEmployeeStatus)) {
			persistedEmployee.setEmployeeStatus(newEmployeeStatus);
		}else {
			throw new BusinessValidationException(oldEmployeeStatus + " cannot be changed to "+newEmployeeStatus);
		}
		
		if (isEmployeeApprovedEligible(persistedEmployee)) {
			persistedEmployee.setEmployeeStatus(Status.APPROVED);
			persistedEmployee.setEmployeeSecurityCheckStatus(null);
			persistedEmployee.setEmployeeWorkPermitStatus(null);
		}
		
		
		ModelMapper modelMapper = new ModelMapper();
		return  modelMapper.map(employeeRepository.save(persistedEmployee), EmployeeDto.class);
	}
	
	
	private boolean isEmployeeApprovedEligible(Employee employee) {
		if (employee.getEmployeeStatus() == Status.IN_CHECK &&
			employee.getEmployeeSecurityCheckStatus() == SecurityCheckStatus.SECURITY_CHECK_FINISHED &&
			employee.getEmployeeWorkPermitStatus() == WorkPermitStatus.WORK_PERMIT_CHECK_FINISHED
				) {
			return true;
		} else {
			return false;
		}
		
	}


	@Override
	public EmployeeDto getEmployeeDetails(Long id) {
		Employee persistedEmployee = employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorReason.EMPLOYEE_NOT_FOUND+" with id: " +id));
		ModelMapper modelMapper = new ModelMapper();
		EmployeeDto result = modelMapper.map(persistedEmployee, EmployeeDto.class);
		
		return result;
	}


}
