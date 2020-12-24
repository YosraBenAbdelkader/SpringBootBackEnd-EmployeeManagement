package com.javademo.springbootbackend.controller;

import com.javademo.springbootbackend.exception.ResourceNotFoundException;
import com.javademo.springbootbackend.model.Employee;
import com.javademo.springbootbackend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/employee")
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

//Create employee
    @PostMapping("/employee")
    public Employee createEmployee(@RequestBody Employee employee){

        return  employeeRepository.save(employee);
    }
// get employee by id

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee>  getEmployeeById (@PathVariable Long id)
    {
        Employee employee= employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("employee not exist with id: "+ id));
        return ResponseEntity.ok(employee);
    }
    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody Employee empl){
        Employee employee= employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("employee not exist with id: "+ id));
        employee.setFirstName(empl.getFirstName());
        employee.setLastName(empl.getLastName());
        employee.setEmailId(empl.getEmailId());
        Employee employeeUpdated= employeeRepository.save(employee);
        return  ResponseEntity.ok(employeeUpdated);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Map<String,Boolean>>  deleteEmployee(@PathVariable long id){
        Employee employee= employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("employee not exist with id: "+ id));
        employeeRepository.delete(employee);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return  ResponseEntity.ok(response);
    }


}