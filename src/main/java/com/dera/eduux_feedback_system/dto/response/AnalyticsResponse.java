package com.dera.eduux_feedback_system.dto.response;

import lombok.*;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnalyticsResponse {
    private Double averageRating;
    private Long totalFeedback;
    private Map<String, Long> feedbackByCategory;
    private Map<String, Long> feedbackByDate;
    private Map<Integer, Long> feedbackByRating;
}