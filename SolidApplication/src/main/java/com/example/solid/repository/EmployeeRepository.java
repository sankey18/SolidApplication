package com.example.solid.repository;

import com.example.solid.model.Employee;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends ReactiveMongoRepository<Employee, String> {

}
