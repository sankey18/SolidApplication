package com.example.solid.controller;

import com.example.solid.model.Training;
import com.example.solid.service.TrainingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/trainings")
public class TrainingController {

    private final TrainingService trainingService;


    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @Operation(
            summary = "Get all Trainings",
            description = "Fetches all trainings from the database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of employees",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Training.class)))
            }
    )
    @GetMapping
    public Flux<Training> getAll(){
        return trainingService.getAll();
    }


    @Operation(
            summary = "Get Training by ID",
            description = "Fetch an training by their unique ID",
            parameters = {
                    @Parameter(name = "id", description = "Training ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Training found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Training.class))),
                    @ApiResponse(responseCode = "404", description = "Training not found", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public Mono<Training> getById(@PathVariable String id) {
        return trainingService.getById(id);
    }

}
