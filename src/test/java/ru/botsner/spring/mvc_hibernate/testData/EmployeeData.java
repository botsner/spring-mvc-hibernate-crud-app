package ru.botsner.spring.mvc_hibernate.testData;

import ru.botsner.spring.mvc_hibernate.entity.Employee;

import java.util.Arrays;
import java.util.List;

public class EmployeeData {

    private static Employee emp1 = new Employee("Ivan", "Petrov", "HR", 900);
    private static Employee emp2 = new Employee("Timur", "Stepanov", "Sales", 2000);

    public static List<Employee> allEmployees() {
        return Arrays.asList(emp1, emp2);
    }
}
