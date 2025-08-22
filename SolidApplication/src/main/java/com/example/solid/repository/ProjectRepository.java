package com.example.solid.repository;

import com.example.solid.model.Project;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProjectRepository extends ReactiveMongoRepository<Project, String> {
}
