package eg.com.workmotion.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import eg.com.workmotion.employee.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
