package com.example.solid.repository;

import com.example.solid.model.Training;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TrainingRepository extends ReactiveMongoRepository<Training, String> {
}
