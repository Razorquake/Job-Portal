package com.razorquake.job.service;

import com.razorquake.job.dto.JobSkillRequest;
import com.razorquake.job.dto.JobSkillResponse;
import com.razorquake.job.model.JobSkill;

import java.util.List;
import java.util.Set;

public interface JobSkillService {

    JobSkillResponse createJobSkill(JobSkillRequest jobSkillRequest);

    List<JobSkillResponse> getAllJobSkills();

    JobSkillResponse getJobSkillById(Long id);

    JobSkillResponse updateJobSkill(Long id, JobSkillRequest jobSkillRequest);

    void deleteJobSkill(Long id);

    Set<JobSkill> getJobSkillsByIds(Set<Long> ids);
}
