package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;


@RestController
public class EmployeeController {
@Autowired
EmployeeService empService;

@PostMapping("/add")

public Employee insertEmployee(@RequestBody Employee emp)
{
return empService.addEmployee(emp);
}

@GetMapping("/get")

public List<Employee> getAllEmployee()
{
return empService.getEmployee();
}
}
