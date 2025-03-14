package com.example.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginDTO {

    @NotBlank(message = "email required")
    private String email;
    @NotBlank(message = "password required")
    private String password;
}
