package ru.botsner.spring.mvc_hibernate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.botsner.spring.mvc_hibernate.dao.EmployeeDAO;
import ru.botsner.spring.mvc_hibernate.entity.Employee;
import ru.botsner.spring.mvc_hibernate.testData.EmployeeData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeDAO employeeDAO; // employeeRepo

    private EmployeeService service;

    private Employee employee;

    @BeforeEach
    void setUp() {
        service = new EmployeeServiceImpl(employeeDAO);
        employee = new Employee();
    }

    @Test
    void getAllEmployees() {
        Mockito.doReturn(EmployeeData.allEmployees()).when(employeeDAO).getAllEmployees();
        List<Employee> empList = service.getAllEmployees();
        Mockito.verify(employeeDAO, Mockito.times(1))
                .getAllEmployees();

        assertIterableEquals(EmployeeData.allEmployees(), empList);
    }

    @Test
    void saveEmployee() {
        service.saveEmployee(employee);
        Mockito.verify(employeeDAO, Mockito.times(1))
                .saveEmployee(employee);
    }

    @Test
    void getEmployee() {
        employee.setId(15);
        Mockito.doReturn(employee).when(employeeDAO).getEmployee(15);

        Employee emp = service.getEmployee(15);

        assertEquals(15, emp.getId());
        assertSame(employee, emp);

        Mockito.verify(employeeDAO, Mockito.times(1))
                .getEmployee(15);
    }

    @Test
    void deleteEmployee() {
        service.deleteEmployee(15);
        Mockito.verify(employeeDAO, Mockito.times(1))
                .deleteEmployee(15);
    }
}