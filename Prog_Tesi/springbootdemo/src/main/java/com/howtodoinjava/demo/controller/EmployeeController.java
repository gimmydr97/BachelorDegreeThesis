package com.howtodoinjava.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.howtodoinjava.demo.model.Employee;

@RestController
@RequestMapping("/")
public class EmployeeController {
	
	List<Employee> employeesList = new ArrayList<Employee>();
	
	@RequestMapping(method = RequestMethod.GET)
    public List<Employee> getEmployees() 
    {
		return employeesList;
    }
	@RequestMapping(method = RequestMethod.POST)
	public String saveEmployees(@RequestBody String e) {
		employeesList.add(new Employee(e));
		return "OK";
	}
	

}
