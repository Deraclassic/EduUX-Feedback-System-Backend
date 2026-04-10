package com.dera.eduux_feedback_system.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackResponse {
    private Long id;
    private String studentName;
    private String studentEmail;
    private String courseName;
    private String systemUsed;
    private Integer rating;
    private String comment;
    private String categoryName;
    private LocalDateTime createdAt;
}
