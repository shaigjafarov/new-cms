package com.guavapay.cms.orderservice.controller;//package az.pasha.controller;
//
//import az.pasha.client.EmployerClient;
//import az.pasha.domain.model.EmployeeDto;
//import az.pasha.service.EmployeeService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("v2/employee")
//public class EmployeeController {
//
//    private final EmployerClient employerClient;
//
//    @GetMapping
//    public ResponseEntity<EmployeeDto> getEmployeeList() {
//        return ResponseEntity.ok(employeeService.getEmployeeList().get(0));
//    }
//
//    @PostMapping("/create")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void createEmployee(@RequestBody EmployeeDto employeeDto) {
//        employeeService.createEmployee(employeeDto);
//    }
//
//    @PutMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public void updateEmployee(@RequestParam Long id, @RequestBody EmployeeDto employeeDto) {
//        employeeService.updateEmployee(id, employeeDto);
//    }
//
//}
