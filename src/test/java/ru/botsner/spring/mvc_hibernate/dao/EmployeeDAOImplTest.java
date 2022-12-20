package ru.botsner.spring.mvc_hibernate.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.botsner.spring.mvc_hibernate.entity.Employee;
import ru.botsner.spring.mvc_hibernate.testData.EmployeeData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/applicationContextTest.xml")
@Sql(value = {"/reset-employee-autoincrement.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class EmployeeDAOImplTest {

    @Autowired
    EmployeeDAO employeeDAO;

    Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee("Timur", "Stepanov", "Sales", 1600);
    }

    @Test
    void daoLoads() {
        assertNotNull(employeeDAO);
    }

    @Test
    @Transactional
    @Rollback
    void getAllEmployees() {
        EmployeeData.allEmployees().forEach(employeeDAO::saveEmployee);

        List<Employee> employees = employeeDAO.getAllEmployees();

        assertEquals(EmployeeData.allEmployees().size(), employees.size());
    }

    @Test
    @Transactional
    @Rollback
    void saveEmployee() {
        employeeDAO.saveEmployee(employee);

        List<Employee> employees = employeeDAO.getAllEmployees();

        assertEquals(1, employees.size());

        assertEquals(employee.getName(), employees.get(0).getName());
        assertEquals(employee.getSurname(), employees.get(0).getSurname());
        assertEquals(employee.getDepartment(), employees.get(0).getDepartment());
        assertEquals(employee.getSalary(), employees.get(0).getSalary());
    }

    @Test
    @Transactional
    @Rollback
    void setIdToNewEmployee() {
        assertEquals(0, employee.getId());

        employeeDAO.saveEmployee(employee);
        assertNotEquals(0, employee.getId());
    }

    @Test
    @Transactional
    @Rollback
    void updateEmployee() {
        employeeDAO.saveEmployee(employee);

        employee.setDepartment("IT");
        employee.setSalary(2500);
        employeeDAO.saveEmployee(employee);

        Employee updatedEmp = employeeDAO.getEmployee(1);

        assertEquals("Timur", updatedEmp.getName());
        assertEquals("IT", updatedEmp.getDepartment());
        assertEquals(2500, updatedEmp.getSalary());
    }

    @Test
    @Transactional
    @Rollback
    void getEmployee() {
        employeeDAO.saveEmployee(employee);

        Employee emp = employeeDAO.getEmployee(1);

        assertEquals(1, emp.getId());
        assertEquals("Timur", emp.getName());
        assertEquals("Stepanov", emp.getSurname());
        assertEquals("Sales", emp.getDepartment());
        assertEquals(1600, emp.getSalary());
    }

    @Test
    @Transactional
    @Rollback
    void deleteEmployee() {
        employeeDAO.saveEmployee(employee);

        List<Employee> employees = employeeDAO.getAllEmployees();
        assertEquals(1, employees.size());

        employeeDAO.deleteEmployee(1);
        employees = employeeDAO.getAllEmployees();

        assertEquals(0, employees.size());
    }
}