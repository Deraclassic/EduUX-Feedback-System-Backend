package com.dera.eduux_feedback_system.controller;

import com.dera.eduux_feedback_system.dto.response.ApiResponse;
import com.dera.eduux_feedback_system.dto.response.FeedbackResponse;
import com.dera.eduux_feedback_system.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/feedback")
@RequiredArgsConstructor
public class AdminFeedbackController {

    private final FeedbackService feedbackService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<FeedbackResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(feedbackService.getAllFeedback(), "All feedback retrieved"));
    }

    @GetMapping("/filter/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<FeedbackResponse>>> byCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(ApiResponse.success(feedbackService.filterByCategory(categoryId), "Filtered by category"));
    }

    @GetMapping("/filter/rating/{rating}")
    public ResponseEntity<ApiResponse<List<FeedbackResponse>>> byRating(@PathVariable Integer rating) {
        return ResponseEntity.ok(ApiResponse.success(feedbackService.filterByRating(rating), "Filtered by rating"));
    }
}
