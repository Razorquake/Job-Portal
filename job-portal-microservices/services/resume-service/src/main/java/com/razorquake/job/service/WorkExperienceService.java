package com.razorquake.job.service;

import com.razorquake.job.dto.WorkExperienceRequest;
import com.razorquake.job.dto.WorkExperienceResponse;
import java.util.List;

public interface WorkExperienceService {

    WorkExperienceResponse addWorkExperience(
            Long resumeId,
            Long candidateId,
            WorkExperienceRequest request
    );

    List<WorkExperienceResponse> getWorkExperiencesByResumeId(Long resumeId);

    WorkExperienceResponse updateWorkExperience(
            Long resumeId,
            Long candidateId,
            Long workExperienceId,
            WorkExperienceRequest request
    );

    void deleteWorkExperience(
            Long resumeId,
            Long candidateId,
            Long workExperienceId
    );

}
