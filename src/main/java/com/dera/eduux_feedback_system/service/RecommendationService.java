package com.dera.eduux_feedback_system.service;

import com.dera.eduux_feedback_system.dto.response.RecommendationResponse;
import com.dera.eduux_feedback_system.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final FeedbackRepository feedbackRepository;

    private static final Map<String, String> SUGGESTION_MAP = Map.of(
            "Slow System",           "Consider optimizing server response time and enabling caching layers.",
            "Confusing Interface",   "Conduct a UX audit and simplify the navigation structure.",
            "Login Problems",        "Review authentication flows and consider adding SSO support.",
            "Poor Navigation",       "Restructure the information architecture and add a sitemap.",
            "Accessibility Issue",   "Audit the platform against WCAG 2.1 guidelines.",
            "Feature Request",       "Review user feature requests and prioritize in next sprint.",
            "Other",                 "Collect more specific feedback to identify the root cause."
    );

    private static final long THRESHOLD = 1;

    public List<RecommendationResponse> getRecommendations() {
        List<Object[]> categoryData = feedbackRepository.countFeedbackByCategory();

        return categoryData.stream()
                .filter(row -> (Long) row[1] >= THRESHOLD)
                .map(row -> {
                    String category = (String) row[0];
                    Long count = (Long) row[1];
                    String suggestion = SUGGESTION_MAP.getOrDefault(category,
                            "Review reported issues and engage users for more detail.");
                    return RecommendationResponse.builder()
                            .issueName(category)
                            .reportCount(count)
                            .suggestion(suggestion)
                            .build();
                })
                .collect(Collectors.toList());
    }
}
