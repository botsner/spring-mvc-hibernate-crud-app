package ru.botsner.spring.mvc_hibernate.service;


import ru.botsner.spring.mvc_hibernate.entity.Employee;

import java.util.List;

public interface EmployeeService {

    public List<Employee> getAllEmployees();

    public void saveEmployee(Employee employee);

    public Employee getEmployee(int id);

    public void deleteEmployee(int id);
}
