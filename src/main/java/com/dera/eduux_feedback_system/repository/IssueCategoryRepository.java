package com.dera.eduux_feedback_system.repository;

import com.dera.eduux_feedback_system.entity.IssueCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IssueCategoryRepository extends JpaRepository<IssueCategory, Long> {
    Optional<IssueCategory> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
}
