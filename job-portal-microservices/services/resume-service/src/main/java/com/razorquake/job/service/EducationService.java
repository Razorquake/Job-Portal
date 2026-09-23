package com.razorquake.job.service;

import com.razorquake.job.dto.EducationRequest;
import com.razorquake.job.dto.EducationResponse;

import java.util.List;

public interface EducationService {

    EducationResponse addEducation(
            Long resumeId,
            Long candidateId,
            EducationRequest educationRequest
    );

    List<EducationResponse> getEducationsByResumeId(Long resumeId);

    EducationResponse updateEducation(
            Long resumeId,
            Long candidateId,
            Long educationId,
            EducationRequest educationRequest
    );

    void deleteEducation(
            Long resumeId,
            Long candidateId,
            Long educationId
    );
}
