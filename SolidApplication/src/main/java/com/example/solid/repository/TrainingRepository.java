package com.example.solid.repository;

import com.example.solid.model.Trainings;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TrainingRepository extends ReactiveMongoRepository<Trainings, String> {
}
