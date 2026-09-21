package com.razorquake.job.service;

import com.razorquake.job.dto.CreateResumeRequest;
import com.razorquake.job.dto.ResumeResponse;
import com.razorquake.job.dto.PersonalInfoResponse;
import com.razorquake.job.model.Resume;

import java.util.List;

public interface ResumeService {

    ResumeResponse createResume(
            Long candidateId,
            CreateResumeRequest request
    );

    ResumeResponse getResume(
            Long candidateId,
            Long resumeId
    );

    List<ResumeResponse> getResumes(
            Long candidateId
    );

    void deleteResume(
            Long candidateId,
            Long resumeId
    );

    ResumeResponse updatePersonalInfo(
            Long candidateId,
            Long resumeId,
            PersonalInfoResponse personalInfo
    );

    ResumeResponse updateSummary(
            Long candidateId,
            Long resumeId,
            String summary
    );

    ResumeResponse setDefaultResume(
            Long candidateId,
            Long resumeId
    );

    Resume getResumeEntity(Long resumeId, Long candidateId);
}
