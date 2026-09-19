package com.razorquake.job.service;

import com.razorquake.job.dto.JobTagRequest;
import com.razorquake.job.dto.JobTagResponse;
import com.razorquake.job.model.JobTag;

import java.util.List;
import java.util.Set;

public interface JobTagService {

    JobTagResponse createJobTag(JobTagRequest jobTagRequest);

    List<JobTagResponse> getAllJobTags();

    JobTagResponse getJobTagById(Long id);

    void deleteJobTagById(Long id);

    JobTagResponse updateJobTagById(Long id, JobTagRequest jobTagRequest);

    Set<JobTag> getJobTagsByIds(Set<Long> jobTagIds);
}
