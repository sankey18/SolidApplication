package com.example.solid.repository;

import com.example.solid.model.Customers;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CustomerRepository extends ReactiveMongoRepository<Customers, String> {
}
