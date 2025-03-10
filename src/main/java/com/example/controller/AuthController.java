package com.example.controller;

import com.example.dto.ApiResponse;
import com.example.dto.auth.RegistrationDTO;
import com.example.enums.AppLanguage;
import com.example.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private AuthService authService;

   /* @PostMapping("/registration")
    public ResponseEntity<ApiResponse<String>> registration(@Valid @RequestBody RegistrationDTO dto,
                                                            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage language) {
        authService.registration(dto,language);
        return ApiResponse.ok();
    }*/
}
