package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.*;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDto {
    private Long id;
    @NotNull(message = "name required")
    private String name;
    private Boolean visible;
    private LocalDateTime createdDate;
}
