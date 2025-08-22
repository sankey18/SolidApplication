package com.example.solid.service;

import com.example.solid.exception.ProjectNotFoundException;
import com.example.solid.model.Project;
import com.example.solid.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Flux<Project> getAllProjects(){
        return projectRepository.findAll()
                .switchIfEmpty(Mono.error(new RuntimeException("No Projects Data Available")));
    }

    public Mono<Project> create(Project projects) {
        return projectRepository.save(projects)
                .onErrorMap(ex -> new RuntimeException("Failed to create project : "+ex.getMessage()));
    }

    public Mono<Project> getById(String id) {
        return projectRepository.findById(id)
                .switchIfEmpty(Mono.error(new ProjectNotFoundException("Not Project is assigned against id:"+id)));
    }

    public Mono<Void> delete(String id) {
        return projectRepository.findById(id)
                .switchIfEmpty(Mono.error(new ProjectNotFoundException("No Project Found for id:->"+id)))
                .flatMap(projectRepository::delete);
    }

    public Mono<Project> update(String id, Project projects) {
        return projectRepository.findById(id)
                .switchIfEmpty(Mono.error(new ProjectNotFoundException("No project Found for id:->"+id)))
                .flatMap(
                        existing ->
                                projectRepository.save(
                                        Project.builder()
                                                .id(id)
                                                .name(projects.getName())
                                                .description(projects.getDescription())
                                                .customerId(projects.getCustomerId())
                                                .build()
                                )
                );
    }

    public Flux<Project> getAllPaginated(int page, int size) {
        return projectRepository.findAll()
                .skip((long) page * size)
                .take(size);
    }
}
