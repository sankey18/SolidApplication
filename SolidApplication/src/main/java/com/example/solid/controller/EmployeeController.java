package com.example.solid.controller;

import com.example.solid.model.dto.EmployeeDTO;
import com.example.solid.model.Employees;
import com.example.solid.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {


    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(
            summary = "Get all Employees",
            description = "Fetches all employees from the database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of employees",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Employees.class)))
            }
    )
    @GetMapping
    public Flux<Employees> getAll(){
        return employeeService.getAll();
    }

    @Operation(
            summary = "Create a new Employee",
            description = "Adds a new employee to the database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employee created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Employees.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
            }
    )
    @PostMapping
    public Mono<Employees> create(@RequestBody Employees emp) {
        return employeeService.create(emp);
    }

    @Operation(
            summary = "Get Employee by ID",
            description = "Fetch an employee by their unique ID",
            parameters = {
                    @Parameter(name = "id", description = "Employee ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employee found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Employees.class))),
                    @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public Mono<Employees> getById(@PathVariable String id) {
        return employeeService.getById(id);
    }

    @Operation(
            summary = "Update Employee",
            description = "Update employee details by ID",
            parameters = {
                    @Parameter(name = "id", description = "Employee ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employee updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Employees.class))),
                    @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content)
            }
    )
    @PutMapping("/{id}")
    public Mono<Employees> update(@PathVariable String id, @RequestBody Employees emp) {
        return employeeService.update(id, emp);
    }

    @Operation(
            summary = "Delete Employee",
            description = "Delete an employee by ID",
            parameters = {
                    @Parameter(name = "id", description = "Employee ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employee deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content)
            }
    )
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return employeeService.delete(id);
    }


    @Operation(
            summary = "Higher Salary Employee",
            description = "Higher Salary",
            parameters = {
                    @Parameter(name = "salary", description = "Salary", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Fetched higher salary"),
                    @ApiResponse(responseCode = "404", description = "Salary not found", content = @Content)
            }
    )
    @GetMapping("/high-salary")
    public Flux<Employees> getHighSalaryEmployees(@RequestParam double salary) {
        return employeeService.getHighSalaryEmployees(salary);
    }

    @Operation(
            summary = "Get all Employees Details",
            description = "Fetches all employees from the database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of employees",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Employees.class)))
            }
    )
    @GetMapping("/allEmployeeData")
    public Flux<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

}
