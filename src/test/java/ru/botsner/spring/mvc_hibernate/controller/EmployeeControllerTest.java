package ru.botsner.spring.mvc_hibernate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.botsner.spring.mvc_hibernate.entity.Employee;
import ru.botsner.spring.mvc_hibernate.service.EmployeeService;
import ru.botsner.spring.mvc_hibernate.testData.EmployeeData;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new EmployeeController(employeeService))
                .build();
    }

    @Test
    void mockMvcLoads() {
        assertNotNull(mockMvc);
    }

    @Test
    void showAllEmployees() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(EmployeeData.allEmployees());
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("all-employees"))
                .andExpect(model().attribute("allEmps", hasSize(2)))
                .andExpect(model().attribute("allEmps", hasItem(
                        allOf(
                                hasProperty("name", is("Ivan")),
                                hasProperty("surname", is("Petrov")),
                                hasProperty("salary", is(900))
                        )
                )))
                .andExpect(model().attribute("allEmps", hasItem(
                        allOf(
                                hasProperty("name", is("Timur")),
                                hasProperty("surname", is("Stepanov")),
                                hasProperty("salary", is(2000))
                        )
                )));
        verify(employeeService, times(1)).getAllEmployees();
        verifyNoMoreInteractions(employeeService);
    }

    @Test
    void addNewEmployee() throws Exception {
        mockMvc.perform(get("/addNewEmployee"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee-info"))
                .andExpect(model().attribute("employee", hasProperty("id", is(0))))
                .andExpect(model().attribute("employee", hasProperty("name", nullValue())))
                .andExpect(model().attribute("employee", hasProperty("surname", nullValue())))
                .andExpect(model().attribute("employee", hasProperty("salary", is(0))));
    }

    @Test
    void saveEmployee() throws Exception {
        mockMvc.perform(post("/saveEmployee"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
                .andExpect(redirectedUrl("/"))
                .andExpect(model().attribute("employee", notNullValue()));
        verify(employeeService, times(1)).saveEmployee(any());
        verifyNoMoreInteractions(employeeService);
    }

    @Test
    void updateEmployee() throws Exception {
        Employee employee = new Employee("Petr", "Ivanov", "HR", 900);
        employee.setId(1);
        when(employeeService.getEmployee(1)).thenReturn(employee);
        mockMvc.perform(get("/updateInfo?empId=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee-info"))
                .andExpect(model().attribute("employee", hasProperty("id", is(1))))
                .andExpect(model().attribute("employee", hasProperty("name", is("Petr"))))
                .andExpect(model().attribute("employee", hasProperty("surname", is("Ivanov"))))
                .andExpect(model().attribute("employee", hasProperty("department", is("HR"))))
                .andExpect(model().attribute("employee", hasProperty("salary", is(900))));
        verify(employeeService, times(1)).getEmployee(1);
        verifyNoMoreInteractions(employeeService);
    }

    @Test
    void deleteEmployee() throws Exception {
        mockMvc.perform(get("/deleteEmployee?empId=1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
                .andExpect(redirectedUrl("/"));
        verify(employeeService, times(1)).deleteEmployee(1);
        verifyNoMoreInteractions(employeeService);
    }
}