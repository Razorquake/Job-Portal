package com.razorquake.job.mapper;

import com.razorquake.job.dto.ResumeSkillRequest;
import com.razorquake.job.dto.ResumeSkillResponse;
import com.razorquake.job.model.Resume;
import com.razorquake.job.model.ResumeSkill;

public class ResumeSkillMapper {

    public static ResumeSkillResponse toResponse(ResumeSkill resumeSkill) {
        return ResumeSkillResponse.builder()
                .id(resumeSkill.getId())
                .skillName(resumeSkill.getSkill())
                .proficiencyLevel(resumeSkill.getProficiencyLevel())
                .yearsOfExperience(resumeSkill.getYearsOfExperience())
                .displayOrder(resumeSkill.getDisplayOrder())

                .build();
    }

    public static ResumeSkill toModel(ResumeSkillRequest resumeSkillRequest, Resume resume) {
        return ResumeSkill.builder()
                .skill(resumeSkillRequest.getSkillName())
                .proficiencyLevel(resumeSkillRequest.getProficiencyLevel())
                .yearsOfExperience(resumeSkillRequest.getYearsOfExperience())
                .displayOrder(resumeSkillRequest.getDisplayOrder())
                .resume(resume)
                .build();
    }
}
