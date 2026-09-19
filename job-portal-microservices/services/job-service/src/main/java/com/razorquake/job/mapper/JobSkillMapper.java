package com.razorquake.job.mapper;

import com.razorquake.job.dto.JobSkillRequest;
import com.razorquake.job.dto.JobSkillResponse;
import com.razorquake.job.model.JobSkill;

public class JobSkillMapper {

    public static JobSkill toEntity(JobSkillRequest jobSkillRequest, String slug) {
        return JobSkill.builder()
                .name(jobSkillRequest.getName())
                .slug(slug)
                .category(jobSkillRequest.getCategory())
                .active(true)
                .build();
    }

    public static JobSkillResponse toResponse(JobSkill jobSkill) {
        return JobSkillResponse.builder()
                .id(jobSkill.getId())
                .name(jobSkill.getName())
                .slug(jobSkill.getSlug())
                .category(jobSkill.getCategory())
                .active(jobSkill.getActive())
                .build();
    }
}
