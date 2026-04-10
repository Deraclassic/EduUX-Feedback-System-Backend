package com.dera.eduux_feedback_system.repository;

import com.dera.eduux_feedback_system.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    List<Feedback> findByUserId(Long userId);

    List<Feedback> findByCategoryId(Long categoryId);

    List<Feedback> findByRating(Integer rating);

    @Query("SELECT AVG(f.rating) FROM Feedback f")
    Double findAverageRating();

    @Query("SELECT f.category.name, COUNT(f) FROM Feedback f GROUP BY f.category.name ORDER BY COUNT(f) DESC")
    List<Object[]> countFeedbackByCategory();

    @Query("SELECT FUNCTION('DATE', f.createdAt), COUNT(f) FROM Feedback f GROUP BY FUNCTION('DATE', f.createdAt) ORDER BY FUNCTION('DATE', f.createdAt)")
    List<Object[]> countFeedbackByDate();
}
