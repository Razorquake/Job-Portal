package com.razorquake.job.mapper;

import com.razorquake.job.dto.EducationRequest;
import com.razorquake.job.dto.EducationResponse;
import com.razorquake.job.model.Education;
import com.razorquake.job.model.Resume;

public class EducationMapper {

    public static EducationResponse toResponse(Education education) {
        return EducationResponse.builder()
                .id(education.getId())
                .institution(education.getInstitutionName())
                .degree(education.getDegree())
                .fieldOfStudy(education.getFieldOfStudy())
                .grade(education.getGrade())
                .startDate(education.getStartDate())
                .endDate(education.getEndDate())
                .isCurrentlyStudying(education.getIsCurrentlyAttending())
                .description(education.getDescription())
                .displayOrder(education.getDisplayOrder())
                .build();
    }

    public static Education toEntity(EducationRequest educationRequest, Resume resume) {
        return Education.builder()
                .resume(resume)
                .institutionName(educationRequest.getInstitutionName())
                .degree(educationRequest.getDegree())
                .fieldOfStudy(educationRequest.getFieldOfStudy())
                .grade(educationRequest.getGrade())
                .startDate(educationRequest.getStartDate())
                .endDate(educationRequest.getEndDate())
                .isCurrentlyAttending(educationRequest.getIsCurrentlyStudying())
                .description(educationRequest.getDescription())
                .displayOrder(educationRequest.getDisplayOrder())
                .build();
    }
}
