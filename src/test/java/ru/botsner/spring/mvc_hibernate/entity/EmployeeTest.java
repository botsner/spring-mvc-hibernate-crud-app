package ru.botsner.spring.mvc_hibernate.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeeTest {

    Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee("Timur", "Stepanov", "Sales", 1600);
    }

    @Test
    void getId() {
        assertEquals(0, employee.getId());
    }

    @Test
    void setId() {
        employee.setId(15);
        assertEquals(15, employee.getId());
    }

    @Test
    void getName() {
        assertEquals("Timur", employee.getName());
    }

    @Test
    void setName() {
        employee.setName("Roman");
        assertEquals("Roman", employee.getName());
    }

    @Test
    void getSurname() {
        assertEquals("Stepanov", employee.getSurname());
    }

    @Test
    void setSurname() {
        employee.setSurname("Petrov");
        assertEquals("Petrov", employee.getSurname());
    }

    @Test
    void getDepartment() {
        assertEquals("Sales", employee.getDepartment());
    }

    @Test
    void setDepartment() {
        employee.setDepartment("IT");
        assertEquals("IT", employee.getDepartment());
    }

    @Test
    void getSalary() {
        assertEquals(1600, employee.getSalary());
    }

    @Test
    void setSalary() {
        employee.setSalary(2100);
        assertEquals(2100, employee.getSalary());
    }
}