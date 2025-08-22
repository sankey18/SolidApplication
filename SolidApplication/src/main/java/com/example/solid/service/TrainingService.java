package com.example.solid.service;

import com.example.solid.model.Training;
import com.example.solid.repository.TrainingRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TrainingService {

    private final TrainingRepository trainingRepository;

    public TrainingService(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    public Flux<Training> getAll() {
        return trainingRepository.findAll()
                .switchIfEmpty(Mono.error(new RuntimeException("No records are present for training")));
    }

    public Mono<Training> getById(String id) {
        return trainingRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("No record is present against id")));
    }
}
