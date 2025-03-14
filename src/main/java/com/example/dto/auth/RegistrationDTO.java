package com.example.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationDTO {

    @NotBlank(message = "fullName required")
    private String fullName;
    @NotBlank(message = "email required")
    private String email;
    @NotBlank(message = "password required")
    private String password;


}
