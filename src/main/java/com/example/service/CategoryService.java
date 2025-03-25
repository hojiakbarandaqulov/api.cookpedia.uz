package com.example.service;

import com.example.dto.ApiResponse;
import com.example.dto.CategoryDto;
import com.example.entity.CategoryEntity;
import com.example.repository.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public ApiResponse<CategoryDto> create(@Valid CategoryDto dto) {
        CategoryEntity entity = new CategoryEntity();
        entity.setName(dto.getName());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setVisible(true);
        categoryRepository.save(entity); // save profile

        CategoryDto responseDto = new CategoryDto();
        responseDto.setName(entity.getName());
        responseDto.setCreatedDate(entity.getCreatedDate());
        return ApiResponse.ok(responseDto);
    }
}
