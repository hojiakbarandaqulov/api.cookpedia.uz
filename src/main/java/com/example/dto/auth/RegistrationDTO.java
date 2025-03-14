package com.example.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistrationDTO {

    @NotBlank(message = "username required")
    private String fullName;
    @NotBlank(message = "email required")
    private String email;
    @NotBlank(message = "password required")
    private String password;

}
