package com.dera.eduux_feedback_system.config;

import com.dera.eduux_feedback_system.entity.IssueCategory;
import com.dera.eduux_feedback_system.repository.IssueCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final IssueCategoryRepository categoryRepository;

    @Override
    public void run(String... args) {
        List<String> defaultCategories = List.of(
                "Slow System",
                "Confusing Interface",
                "Login Problems",
                "Poor Navigation",
                "Accessibility Issue",
                "Feature Request",
                "Other"
        );

        defaultCategories.forEach(name -> {
            if (!categoryRepository.existsByNameIgnoreCase(name)) {
                categoryRepository.save(IssueCategory.builder().name(name).build());
            }
        });
    }
}
