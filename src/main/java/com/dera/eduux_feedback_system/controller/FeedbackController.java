package com.dera.eduux_feedback_system.controller;

import com.dera.eduux_feedback_system.dto.request.FeedbackRequest;
import com.dera.eduux_feedback_system.dto.response.ApiResponse;
import com.dera.eduux_feedback_system.dto.response.FeedbackResponse;
import com.dera.eduux_feedback_system.service.FeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<ApiResponse<FeedbackResponse>> submit(
            @Valid @RequestBody FeedbackRequest request,
            @AuthenticationPrincipal String email) {
        return ResponseEntity.ok(ApiResponse.success(feedbackService.submit(request, email), "Feedback submitted successfully"));
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<FeedbackResponse>>> getMyFeedback(
            @AuthenticationPrincipal String email) {
        return ResponseEntity.ok(ApiResponse.success(feedbackService.getMyFeedback(email), "Feedback retrieved successfully"));
    }
}
