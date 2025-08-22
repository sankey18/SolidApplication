package com.example.solid.model;

import com.example.solid.enumUtils.Status;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Employee {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    private String name;
    private String role;
    private double salary;
    private Status status;
    private String projectId;
    private String customerId;
    private List<String> trainingIds;

}

