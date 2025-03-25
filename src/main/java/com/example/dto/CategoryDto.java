package com.example.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CategoryDto {
    private Long id;
    @NotNull(message = "name required")
    private String name;
    private Boolean visible;
    private LocalDateTime createdDate;
}
