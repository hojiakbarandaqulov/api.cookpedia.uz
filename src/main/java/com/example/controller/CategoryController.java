package com.example.controller;

import com.example.dto.ApiResponse;
import com.example.dto.CategoryDto;
import com.example.enums.RoleEnum;
import com.example.service.CategoryService;
import com.example.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping({"/create"})
    public ResponseEntity<ApiResponse<CategoryDto>> create(@RequestBody @Valid CategoryDto dto) {
        ApiResponse<CategoryDto> response = categoryService.create(dto);
        return ResponseEntity.ok(response);
    }
}
