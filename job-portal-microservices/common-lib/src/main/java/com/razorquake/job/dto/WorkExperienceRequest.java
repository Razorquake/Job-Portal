package com.razorquake.job.dto;

import com.razorquake.job.domain.JobType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkExperienceRequest {

    @NotBlank(message = "Company name cannot be blank")
    private String companyName;

    private String companyLogoUrl;

    @NotBlank(message = "Job title cannot be blank")
    private String jobTitle;

    private JobType employmentType;
    private String location;

    @NotNull(message = "Start date cannot be null")
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Boolean isCurrentJob = false;

    private String description;
    private List<String> technologies;
    private Integer displayOrder;
}
