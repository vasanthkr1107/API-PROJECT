package com.example.demo.service;

import com.example.demo.entity.Employee;
import java.util.List;

public interface EmployeeService {
    Employee addEmployee(Employee emp);
    List<Employee> getEmployee();
}
