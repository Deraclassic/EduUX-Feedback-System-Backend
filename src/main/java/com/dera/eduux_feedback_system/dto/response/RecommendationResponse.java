package com.dera.eduux_feedback_system.dto.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationResponse {
    private String issueName;
    private Long reportCount;
    private String suggestion;
}
