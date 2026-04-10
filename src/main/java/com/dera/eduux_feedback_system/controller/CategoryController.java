package com.dera.eduux_feedback_system.controller;

import com.dera.eduux_feedback_system.dto.response.ApiResponse;
import com.dera.eduux_feedback_system.dto.response.CategoryResponse;
import com.dera.eduux_feedback_system.repository.IssueCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final IssueCategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAll() {
        List<CategoryResponse> categories = categoryRepository.findAll()
                .stream()
                .map(c -> CategoryResponse.builder().id(c.getId()).name(c.getName()).build())
                .toList();
        return ResponseEntity.ok(ApiResponse.success(categories, "Categories retrieved"));
    }
}
