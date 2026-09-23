package com.razorquake.job.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;
    private List<String> technologies;

    @Pattern(regexp = "^(https?://).", message = "Invalid URL format")
    private String projectUrl;

    @Pattern(regexp = "^(https?://).", message = "Invalid URL format")
    private String sourceCodeUrl;

    private LocalDate startDate;
    private LocalDate endDate;

    @Builder.Default
    private Boolean isOngoing = false;

    private Integer displayOrder;
}
