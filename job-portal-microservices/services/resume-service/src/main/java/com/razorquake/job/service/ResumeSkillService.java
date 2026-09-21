package com.razorquake.job.service;

import com.razorquake.job.dto.ResumeSkillRequest;
import com.razorquake.job.dto.ResumeSkillResponse;

import java.util.List;

public interface ResumeSkillService {

    ResumeSkillResponse addResumeSkill(
            Long resumeId,
            Long candidateId,
            ResumeSkillRequest resumeSkillRequest
    );

    List<ResumeSkillResponse> getResumeSkillsByResumeId(Long resumeId);

    ResumeSkillResponse updateResumeSkill(
            Long resumeId,
            Long candidateId,
            Long resumeSkillId,
            ResumeSkillRequest resumeSkillRequest
    );

    void deleteResumeSkill(
            Long resumeId,
            Long candidateId,
            Long resumeSkillId
    );
}
