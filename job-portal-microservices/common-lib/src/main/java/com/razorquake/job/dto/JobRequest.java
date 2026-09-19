package com.razorquake.job.dto;

import com.razorquake.job.domain.ExperienceLevel;
import com.razorquake.job.domain.JobType;
import com.razorquake.job.domain.WorkMode;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    private String requirements;
    private String responsibilities;
    private String benefits;

    @NotNull(message = "Category is required")
    private Long categoryId;

    private Set<Long> skillIds;

    private Set<Long> tagIds;

    // Location
    private String address;
    private String city;
    private String state;
    private String country;
    private String zipCode;

    // Salary
    @DecimalMin(value = "0.0", message = "Minimum salary must be a non-negative value")
    private BigDecimal minSalary;

    @DecimalMin(value = "0.0", message = "Maximum salary must be a non-negative value")
    private BigDecimal maxSalary;

    // Classification
    private JobType jobType;
    private WorkMode workMode;
    private ExperienceLevel experienceLevel;

    // Posting details
    @Min(value = 1, message = "Openings must be a positive integer")
    private Integer openings;
    private LocalDate applicationDeadline;
    private LocalDate expiresAt;
}
