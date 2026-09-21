package com.razorquake.job.model;

import com.razorquake.job.domain.ProficiencyLevel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "resume_skills")
public class ResumeSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Resume resume;

    @Column(nullable = false)
    private String skill;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ProficiencyLevel proficiencyLevel = ProficiencyLevel.BEGINNER;

    @Column(nullable = false)
    private Integer yearsOfExperience;

    @Column(nullable = false)
    @Builder.Default
    private Integer displayOrder = 0;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}
