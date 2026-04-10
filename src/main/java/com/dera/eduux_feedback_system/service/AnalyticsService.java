package com.dera.eduux_feedback_system.service;

import com.dera.eduux_feedback_system.dto.response.AnalyticsResponse;
import com.dera.eduux_feedback_system.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final FeedbackRepository feedbackRepository;

    public AnalyticsResponse getAnalytics() {
        Double avgRating = feedbackRepository.findAverageRating();
        Long totalFeedback = feedbackRepository.count();

        // Feedback count per category
        List<Object[]> categoryData = feedbackRepository.countFeedbackByCategory();
        Map<String, Long> feedbackByCategory = new LinkedHashMap<>();
        for (Object[] row : categoryData) {
            feedbackByCategory.put((String) row[0], (Long) row[1]);
        }

        // Feedback count per date
        List<Object[]> dateData = feedbackRepository.countFeedbackByDate();
        Map<String, Long> feedbackByDate = new LinkedHashMap<>();
        for (Object[] row : dateData) {
            feedbackByDate.put(row[0].toString(), (Long) row[1]);
        }

        // Feedback count per rating (1–5)
        Map<Integer, Long> feedbackByRating = feedbackRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(f -> f.getRating(), Collectors.counting()));

        return AnalyticsResponse.builder()
                .averageRating(avgRating != null ? Math.round(avgRating * 100.0) / 100.0 : 0.0)
                .totalFeedback(totalFeedback)
                .feedbackByCategory(feedbackByCategory)
                .feedbackByDate(feedbackByDate)
                .feedbackByRating(feedbackByRating)
                .build();
    }
}
