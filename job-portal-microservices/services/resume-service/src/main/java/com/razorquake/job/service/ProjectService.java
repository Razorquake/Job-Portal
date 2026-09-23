package com.razorquake.job.service;

import com.razorquake.job.dto.ProjectRequest;
import com.razorquake.job.dto.ProjectResponse;

import java.util.List;

public interface ProjectService {

    ProjectResponse createProject(
            Long candidateId,
            Long resumeId,
            ProjectRequest projectRequest
    );

    List<ProjectResponse> getProjectsByResumeId(Long resumeId);

    ProjectResponse updateProject(
            Long candidateId,
            Long resumeId,
            Long projectId,
            ProjectRequest projectRequest
    );

    void deleteProject(
            Long candidateId,
            Long resumeId,
            Long projectId
    );
}
