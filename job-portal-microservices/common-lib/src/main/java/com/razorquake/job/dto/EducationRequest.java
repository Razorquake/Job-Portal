package com.razorquake.job.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EducationRequest {

    @NotBlank(message = "Institution cannot be blank")
    private String institutionName;

    @NotBlank(message = "Degree cannot be blank")
    private String degree;

    private String fieldOfStudy;
    private String grade;

    @NotNull(message = "Start date cannot be null")
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder.Default
    private Boolean isCurrentlyStudying = false;

    private String description;
    private Integer displayOrder;
}
