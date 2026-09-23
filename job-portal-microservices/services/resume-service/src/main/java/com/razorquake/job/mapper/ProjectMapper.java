package com.razorquake.job.mapper;

import com.razorquake.job.dto.ProjectRequest;
import com.razorquake.job.dto.ProjectResponse;
import com.razorquake.job.model.Project;
import com.razorquake.job.model.Resume;

import java.util.List;

public class ProjectMapper {

    public static ProjectResponse toProjectResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .technologies(project.getTechnologies())
                .projectUrl(project.getProjectUrl())
                .sourceCodeUrl(project.getSourceCodeUrl())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .isOngoing(project.getIsOngoing())
                .displayOrder(project.getDisplayOrder())
                .build();
    }

    public static Project toProject(ProjectRequest request, Resume resume) {
        return Project.builder()
                .resume(resume)
                .title(request.getTitle())
                .description(request.getDescription())
                .technologies(request.getTechnologies() != null ? request.getTechnologies() : List.of())
                .projectUrl(request.getProjectUrl())
                .sourceCodeUrl(request.getSourceCodeUrl())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .isOngoing(request.getIsOngoing())
                .displayOrder(request.getDisplayOrder() != null ? request.getDisplayOrder() : 0)
                .build();
    }
}
