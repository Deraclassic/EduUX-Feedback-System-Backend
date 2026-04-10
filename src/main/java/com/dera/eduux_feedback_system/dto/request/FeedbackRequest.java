package com.dera.eduux_feedback_system.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class FeedbackRequest {

    @NotBlank(message = "Course name is required")
    private String courseName;

    @NotBlank(message = "System used is required")
    private String systemUsed;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    @NotNull(message = "Rating is required")
    private Integer rating;

    private String comment;

    @NotNull(message = "Category is required")
    private Long categoryId;
}
