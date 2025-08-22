package com.example.solid.controller;

import com.example.solid.model.Customer;
import com.example.solid.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/customers")
public class CustomerController {


    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(
            summary = "Get all Customers",
            description = "Fetches all customers from the database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of Customers",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Customer.class)))
            }
    )
    @GetMapping
    public Flux<Customer> getAll() {
        return customerService.getAll();
    }

    @Operation(
            summary = "Create a new Customers",
            description = "Adds a new Customers to the database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Customers created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Customer.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
            }
    )
    @PostMapping
    public Mono<Customer> create(@RequestBody Customer customers) {
        return customerService.create(customers);
    }

    @Operation(
            summary = "Get Customer by ID",
            description = "Fetch an Customer by their unique ID",
            parameters = {
                    @Parameter(name = "id", description = "Customer ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employee found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Customer.class))),
                    @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public Mono<Customer> getById(@PathVariable String id) {
        return customerService.getById(id);
    }

    @Operation(
            summary = "Update Customer",
            description = "Update Customer details by ID",
            parameters = {
                    @Parameter(name = "id", description = "Customer ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Customer updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Customer.class))),
                    @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)
            }
    )
    @PutMapping("/{id}")
    public Mono<Customer> update(@PathVariable String id, @RequestBody Customer customers) {
        return customerService.update(id, customers);
    }

    @Operation(
            summary = "Delete Customer",
            description = "Delete an Customer by ID",
            parameters = {
                    @Parameter(name = "id", description = "Customer ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Customer deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)
            }
    )
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return customerService.delete(id);
    }
}
