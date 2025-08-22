package com.example.solid.repository;

import com.example.solid.model.Employees;
import reactor.core.publisher.Flux;

public interface EmployeeCustomRepository {
    Flux<Employees> findEmployeesBySalaryGreaterThan(double salary);
}
