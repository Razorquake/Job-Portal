package com.razorquake.job.service.impl;

import com.razorquake.job.dto.ProjectRequest;
import com.razorquake.job.dto.ProjectResponse;
import com.razorquake.job.exception.NotFoundException;
import com.razorquake.job.exception.UnauthorizedException;
import com.razorquake.job.mapper.ProjectMapper;
import com.razorquake.job.model.Project;
import com.razorquake.job.model.Resume;
import com.razorquake.job.repository.ProjectRepository;
import com.razorquake.job.service.ProjectService;
import com.razorquake.job.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ResumeService resumeService;

    @Override
    public ProjectResponse createProject(
            Long candidateId,
            Long resumeId,
            ProjectRequest projectRequest
    ) {
        Resume resume = resumeService.getResumeEntity(resumeId, candidateId);
        return ProjectMapper.toProjectResponse(
                projectRepository.save(
                        ProjectMapper.toProject(projectRequest, resume)
                )
        );
    }

    @Override
    public List<ProjectResponse> getProjectsByResumeId(Long resumeId) {
        return projectRepository.findByResume_IdOrderByDisplayOrderAsc(resumeId)
                .stream()
                .map(ProjectMapper::toProjectResponse)
                .toList();
    }

    @Override
    public ProjectResponse updateProject(
            Long candidateId,
            Long resumeId,
            Long projectId,
            ProjectRequest projectRequest
    ) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project with id " + projectId + " not found"));
        if (!project.getResume().getId().equals(resumeId)) {
            throw new UnauthorizedException(
                    "Project with id " + projectId +
                            " does not belong to resume with id " + resumeId
            );
        }
        resumeService.getResumeEntity(resumeId, candidateId);
        project.setTitle(projectRequest.getTitle());
        project.setDescription(projectRequest.getDescription());
        project.setTechnologies(
                projectRequest.getTechnologies() != null ?
                        projectRequest.getTechnologies() :
                        List.of()
        );
        project.setProjectUrl(projectRequest.getProjectUrl());
        project.setSourceCodeUrl(projectRequest.getSourceCodeUrl());
        project.setStartDate(projectRequest.getStartDate());
        project.setEndDate(projectRequest.getEndDate());
        project.setIsOngoing(projectRequest.getIsOngoing());
        project.setDisplayOrder(
                projectRequest.getDisplayOrder() != null ?
                        projectRequest.getDisplayOrder() :
                        project.getDisplayOrder()
        );
        return ProjectMapper.toProjectResponse(
                projectRepository.save(project)
        );
    }

    @Override
    public void deleteProject(Long candidateId, Long resumeId, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project with id " + projectId + " not found"));
        if (!project.getResume().getId().equals(resumeId)) {
            throw new UnauthorizedException(
                    "Project with id " + projectId +
                            " does not belong to resume with id " + resumeId
            );
        }
        resumeService.getResumeEntity(resumeId, candidateId);
        projectRepository.delete(project);
    }
}
