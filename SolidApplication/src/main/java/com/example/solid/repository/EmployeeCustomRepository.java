package com.example.solid.repository;

import com.example.solid.model.Employee;
import reactor.core.publisher.Flux;

public interface EmployeeCustomRepository {
    Flux<Employee> findEmployeesBySalaryGreaterThan(double salary);
}
