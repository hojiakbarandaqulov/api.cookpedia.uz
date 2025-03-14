package com.example.controller;

import com.example.dto.ApiResponse;
import com.example.dto.auth.LoginDTO;
import com.example.dto.auth.RegistrationDTO;
import com.example.enums.AppLanguage;
import com.example.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/registration",produces = "application/json")
    public ResponseEntity<ApiResponse<?>> registration(@Valid @RequestBody RegistrationDTO dto,
                                                       @RequestHeader(value = "Accept-Language", defaultValue = "uz") AppLanguage language) {
        ApiResponse<?> registration = authService.registration(dto, language);
        return ResponseEntity.ok().body(registration);
    }


    @PostMapping(value = "/login"/*,produces = "application/json"*/)
    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody LoginDTO dto,
                                                       @RequestHeader(value = "Accept-Language", defaultValue = "uz") AppLanguage language) {
        ApiResponse<?> login = authService.login(dto, language);
        return ResponseEntity.ok(login);
    }

   /* @GetMapping(value = "/verification",produces = "application/json")
    public ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody LoginDTO dto,
                                                @RequestHeader(value = "Accept-Language", defaultValue = "uz") AppLanguage language) {
        ApiResponse<?> login = authService.login(dto, language);
        return ResponseEntity.ok(login);
    }*/

}

