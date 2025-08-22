package com.example.solid.controller;


import com.example.solid.model.Project;
import com.example.solid.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {


    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Operation(
            summary = "Get all Projects",
            description = "Fetches all projects from the database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of projects",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Project.class)))
            }
    )
    @GetMapping
    public Flux<Project> getAllProjects(){
        return projectService.getAllProjects();
    }

    @Operation(
            summary = "Create a new Project",
            description = "Adds a new project to the database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Project created successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Project.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
            }
    )
    @PostMapping
    public Mono<Project> create(@RequestBody Project projects) {
        return projectService.create(projects);
    }

    @Operation(
            summary = "Get Project by ID",
            description = "Fetch an Project by their unique ID",
            parameters = {
                    @Parameter(name = "id", description = "Project ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Project found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Project.class))),
                    @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public Mono<Project> getById(@PathVariable String id) {
        return projectService.getById(id);
    }

    @Operation(
            summary = "Delete Project",
            description = "Delete an Project by ID",
            parameters = {
                    @Parameter(name = "id", description = "Delete ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Project deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Project not found", content = @Content)
            }
    )
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return projectService.delete(id);
    }

    @Operation(
            summary = "Update Project",
            description = "Update Project details by ID",
            parameters = {
                    @Parameter(name = "id", description = "Project ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Project updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Project.class))),
                    @ApiResponse(responseCode = "404", description = "Project not found", content = @Content)
            }
    )
    @PutMapping("/{id}")
    public Mono<Project> update(@PathVariable String id, @RequestBody Project projects) {
        return projectService.update(id, projects);
    }

}
