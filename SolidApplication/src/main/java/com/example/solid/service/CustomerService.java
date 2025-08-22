package com.example.solid.service;

import com.example.solid.exception.CustomerNotFoundException;
import com.example.solid.model.Customers;
import com.example.solid.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {
    public static final String CUSTOMER_NOT_FOUND = "Customer not found: ";
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Flux<Customers> getAll()  {
        return customerRepository.findAll()
                .onErrorMap(ex -> new RuntimeException("Failed to fetch customers", ex));
    }

    public Mono<Customers> create(Customers customer) {
        return customerRepository.save(customer)
                .onErrorMap(ex-> new RuntimeException("Failed to create customer", ex));
    }

    public Mono<Customers> getById(String id) {
        return customerRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomerNotFoundException(CUSTOMER_NOT_FOUND + id)));
    }

    public Mono<Void> delete(String id) {
        return customerRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomerNotFoundException(CUSTOMER_NOT_FOUND + id)))
                .flatMap(customerRepository::delete);
    }

    public Mono<Customers> update(String id, Customers customers) {
        return customerRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomerNotFoundException(CUSTOMER_NOT_FOUND + id)))
                .flatMap(
                        existing ->
                                customerRepository.save(
                                        new Customers(
                                                id,
                                                customers.getName(),
                                                customers.getDomain()
                                        )
                                )

                );
    }

    public Flux<Customers> getAllPaginated(int page, int size) {
        return customerRepository.findAll()
                .skip((long) page * size)
                .take(size);
    }
}
