package com.example.solid.repository;

import com.example.solid.model.Employees;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
public class EmployeeCustomRepositoryImpl implements EmployeeCustomRepository {


    private final ReactiveMongoTemplate mongoTemplate;

    public EmployeeCustomRepositoryImpl(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Flux<Employees> findEmployeesBySalaryGreaterThan(double salary) {
        Aggregation aggregation = newAggregation(
                match(Criteria.where("salary").gt(salary)),
                sort(Sort.by(Sort.Direction.DESC, "salary"))
        );

        return mongoTemplate.aggregate(aggregation, "employees", Employees.class);
    }
}
