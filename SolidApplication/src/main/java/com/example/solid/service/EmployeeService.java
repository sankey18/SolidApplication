package com.example.solid.service;

import com.example.solid.model.dto.EmployeeDTO;
import com.example.solid.exception.EmployeeNotFoundException;
import com.example.solid.model.Customers;
import com.example.solid.model.Employees;
import com.example.solid.model.Projects;
import com.example.solid.model.Trainings;
import com.example.solid.repository.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class EmployeeService {

    public static final String EMPLOYEE_NOT_FOUND = "Employee not found: ";
    private final EmployeeRepository repository;
    private final EmployeeCustomRepositoryImpl employeeCustomRepository;
    private final ProjectRepository projectRepository;
    private final CustomerRepository customerRepository;
    private final TrainingRepository trainingRepository;


    public EmployeeService(EmployeeRepository repository, EmployeeCustomRepositoryImpl employeeCustomRepository, ProjectRepository projectRepository, CustomerRepository customerRepository, TrainingRepository trainingRepository) {
        this.repository = repository;
        this.employeeCustomRepository = employeeCustomRepository;
        this.projectRepository = projectRepository;
        this.customerRepository = customerRepository;
        this.trainingRepository = trainingRepository;
    }

    public Flux<Employees> getAll()  {
        return repository.findAll()
                .switchIfEmpty(Mono.error(new RuntimeException("No employee data available")));
    }

    public Mono<Employees> create(Employees emp) {
        return repository.save(emp)
                .onErrorMap(ex ->new RuntimeException("Failed to create employee :"+ex.getMessage()));
    }

    public Mono<Employees> getById(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND + id)));
    }

    public Mono<Employees> update(String id, Employees emp) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND + id)))
                .flatMap(
                        existing ->
                                repository.save(
                                        Employees.builder()
                                                .id(emp.getId())
                                                .role(emp.getRole())
                                                .name(emp.getName())
                                                .salary(emp.getSalary())
                                                .status(emp.getStatus())
                                                .customerId(emp.getCustomerId())
                                                .projectId(emp.getProjectId())
                                                .trainingIds(emp.getTrainingIds() != null ? emp.getTrainingIds() : List.of())
                                                .build()
                                )

                );
    }

    public Mono<Void> delete(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND + id)))
                .flatMap(repository::delete);
    }


    public Flux<Employees> getAllPaginated(int page, int size) {
        return repository.findAll()
                .skip((long) page * size)
                .take(size);
    }

    public Flux<Employees> getHighSalaryEmployees(double salary) {
        return employeeCustomRepository.findEmployeesBySalaryGreaterThan(salary);
    }

    public Flux<EmployeeDTO> getAllEmployees() {
        return repository.findAll()
                .flatMap(this::mapToDTO);
    }


    private Mono<EmployeeDTO> mapToDTO (Employees emp) {
        Mono<Projects> projectMono = (emp.getProjectId() != null) ?
                projectRepository.findById(emp.getProjectId()) : Mono.empty();

        Mono<Customers> customerMono = (emp.getCustomerId() != null) ?
                customerRepository.findById(emp.getCustomerId()) : Mono.empty();

        Flux<Trainings> trainingsFlux = (emp.getTrainingIds() != null) ?
                trainingRepository.findAllById(emp.getTrainingIds()) : Flux.empty();

        return Mono.zip(Mono.just(emp), projectMono.defaultIfEmpty(new Projects()),
                        customerMono.defaultIfEmpty(new Customers()),
                        trainingsFlux.collectList())
                .map(tuple -> {
                    EmployeeDTO dto = new EmployeeDTO();
                    dto.setId(emp.getId());
                    dto.setName(emp.getName());
                    dto.setRole(emp.getRole());
                    dto.setStatus(emp.getStatus().name());
                    dto.setProjectName(tuple.getT2().getName());
                    dto.setCustomerName(tuple.getT3().getName());
                    dto.setTrainings(tuple.getT4().stream()
                            .map(Trainings::getTitle)
                            .toList());
                    return dto;
                });
    }
}
