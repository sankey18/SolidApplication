package com.example.solid.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Training {
    @Id
    private String id;
    private String title;
    private String description;
    private boolean completed;
}
