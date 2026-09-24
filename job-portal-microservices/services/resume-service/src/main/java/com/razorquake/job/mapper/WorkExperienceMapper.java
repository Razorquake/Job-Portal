package com.razorquake.job.mapper;

import com.razorquake.job.dto.WorkExperienceRequest;
import com.razorquake.job.dto.WorkExperienceResponse;
import com.razorquake.job.model.Resume;
import com.razorquake.job.model.WorkExperience;

import java.util.ArrayList;

public class WorkExperienceMapper {

    public static WorkExperienceResponse toResponse(WorkExperience workExperience) {
        return WorkExperienceResponse.builder()
                .id(workExperience.getId())
                .companyName(workExperience.getCompanyName())
                .companyLogoUrl(workExperience.getCompanyLogoUrl())
                .jobTitle(workExperience.getJobTitle())
                .employmentType(workExperience.getEmploymentType())
                .location(workExperience.getLocation())
                .startDate(workExperience.getStartDate())
                .endDate(workExperience.getEndDate())
                .isCurrentJob(workExperience.getIsCurrentJob())
                .description(workExperience.getDescription())
                .displayOrder(workExperience.getDisplayOrder())
                .technologies(workExperience.getTechnologies())
                .build();
    }

    public static WorkExperience toWorkExperience(WorkExperienceRequest workExperienceRequest, Resume resume) {
        return WorkExperience.builder()
                .resume(resume)
                .companyName(workExperienceRequest.getCompanyName())
                .companyLogoUrl(workExperienceRequest.getCompanyLogoUrl())
                .jobTitle(workExperienceRequest.getJobTitle())
                .employmentType(workExperienceRequest.getEmploymentType())
                .location(workExperienceRequest.getLocation())
                .startDate(workExperienceRequest.getStartDate())
                .endDate(workExperienceRequest.getEndDate())
                .isCurrentJob(workExperienceRequest.getIsCurrentJob())
                .description(workExperienceRequest.getDescription())
                .displayOrder(
                        workExperienceRequest.getDisplayOrder() != null ?
                                workExperienceRequest.getDisplayOrder() :
                                0
                )
                .technologies(
                        workExperienceRequest.getTechnologies() != null ?
                                workExperienceRequest.getTechnologies() :
                                new ArrayList<>()
                )
                .build();
    }
}
