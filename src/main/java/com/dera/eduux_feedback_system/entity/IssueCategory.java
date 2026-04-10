package com.dera.eduux_feedback_system.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "issue_category")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class IssueCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
}
