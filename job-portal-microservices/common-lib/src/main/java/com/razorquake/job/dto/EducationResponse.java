package com.razorquake.job.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class EducationResponse {
    private Long id;
    private String institution;
    private String degree;
    private String fieldOfStudy;
    private String grade;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isCurrentlyStudying;
    private String description;
    private Integer displayOrder;
}
