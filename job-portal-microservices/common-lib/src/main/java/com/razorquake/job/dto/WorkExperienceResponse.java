package com.razorquake.job.dto;

import com.razorquake.job.domain.JobType;
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
public class WorkExperienceResponse {

    private Long id;
    private String companyName;
    private String companyLogoUrl;
    private String jobTitle;
    private JobType employmentType;
    private String location;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean isCurrentJob;
    private String description;
    private List<String> technologies;
    private Integer displayOrder;
}
