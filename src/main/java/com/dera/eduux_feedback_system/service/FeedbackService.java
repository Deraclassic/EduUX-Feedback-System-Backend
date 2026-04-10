package com.dera.eduux_feedback_system.service;

import com.dera.eduux_feedback_system.dto.request.FeedbackRequest;
import com.dera.eduux_feedback_system.dto.response.FeedbackResponse;
import com.dera.eduux_feedback_system.entity.Feedback;
import com.dera.eduux_feedback_system.entity.IssueCategory;
import com.dera.eduux_feedback_system.entity.User;
import com.dera.eduux_feedback_system.exception.ResourceNotFoundException;
import com.dera.eduux_feedback_system.repository.FeedbackRepository;
import com.dera.eduux_feedback_system.repository.IssueCategoryRepository;
import com.dera.eduux_feedback_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final IssueCategoryRepository categoryRepository;

    public FeedbackResponse submit(FeedbackRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        IssueCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));

        Feedback feedback = Feedback.builder()
                .user(user)
                .courseName(request.getCourseName())
                .systemUsed(request.getSystemUsed())
                .rating(request.getRating())
                .comment(request.getComment())
                .category(category)
                .build();

        return mapToResponse(feedbackRepository.save(feedback));
    }

    public List<FeedbackResponse> getMyFeedback(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return feedbackRepository.findByUserId(user.getId())
                .stream().map(this::mapToResponse).toList();
    }

    public List<FeedbackResponse> getAllFeedback() {
        return feedbackRepository.findAll()
                .stream().map(this::mapToResponse).toList();
    }

    public List<FeedbackResponse> filterByCategory(Long categoryId) {
        return feedbackRepository.findByCategoryId(categoryId)
                .stream().map(this::mapToResponse).toList();
    }

    public List<FeedbackResponse> filterByRating(Integer rating) {
        return feedbackRepository.findByRating(rating)
                .stream().map(this::mapToResponse).toList();
    }

    private FeedbackResponse mapToResponse(Feedback f) {
        return FeedbackResponse.builder()
                .id(f.getId())
                .studentName(f.getUser().getName())
                .studentEmail(f.getUser().getEmail())
                .courseName(f.getCourseName())
                .systemUsed(f.getSystemUsed())
                .rating(f.getRating())
                .comment(f.getComment())
                .categoryName(f.getCategory().getName())
                .createdAt(f.getCreatedAt())
                .build();
    }
}
