package com.example.dto.auth;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class UpdatePasswordDTO {
    @NotBlank(message = "username required")
    private String username;

    @NotBlank(message = "confirmPassword required")
    private String confirmPassword;

    @NotBlank(message = "password required")
    private String newPassword;

}
