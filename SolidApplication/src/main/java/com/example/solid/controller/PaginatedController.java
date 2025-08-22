package com.example.solid.controller;

import com.example.solid.model.Customer;
import com.example.solid.model.Employee;
import com.example.solid.model.Project;
import com.example.solid.service.CustomerService;
import com.example.solid.service.EmployeeService;
import com.example.solid.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
public class PaginatedController {

    private final EmployeeService employeeService;
    private final CustomerService customerService;
    private final ProjectService projectService;

    public PaginatedController(EmployeeService employeeService, CustomerService customerService, ProjectService projectService) {
        this.employeeService = employeeService;
        this.customerService = customerService;
        this.projectService = projectService;
    }

    @Operation(
            summary = "Get Employees with Pagination",
            description = "Fetch employees using pagination (page & size params)",
            parameters = {
                    @Parameter(name = "page", description = "Page number (starting from 0)"),
                    @Parameter(name = "size", description = "Page size (default 5)")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Paged employees list",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Employee.class)))
            }
    )
    @GetMapping("/employees/paged")
    public Flux<Employee> getAllPaginatedEmployees(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "5") int size) {
        return employeeService.getAllPaginated(page, size);
    }


    @Operation(
            summary = "Get Customers with Pagination",
            description = "Fetch customers using pagination (page & size params)",
            parameters = {
                    @Parameter(name = "page", description = "Page number (starting from 0)"),
                    @Parameter(name = "size", description = "Page size (default 5)")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Paged customers list",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Customer.class)))
            }
    )
    @GetMapping("/customers/paged")
    public Flux<Customer> getAllPaginatedCustomers(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "5") int size) {
        return customerService.getAllPaginated(page, size);
    }

    @Operation(
            summary = "Get Projects with Pagination",
            description = "Fetch projects using pagination (page & size params)",
            parameters = {
                    @Parameter(name = "page", description = "Page number (starting from 0)"),
                    @Parameter(name = "size", description = "Page size (default 5)")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Paged projects list",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Customer.class)))
            }
    )
    @GetMapping("/projects/paged")
    public Flux<Project> getAllPaginatedProjects(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "5") int size) {
        return projectService.getAllPaginated(page, size);
    }
}
