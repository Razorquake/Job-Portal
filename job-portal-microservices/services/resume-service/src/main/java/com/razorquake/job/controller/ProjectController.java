package com.razorquake.job.controller;

import com.razorquake.job.dto.ApiResponse;
import com.razorquake.job.dto.ProjectRequest;
import com.razorquake.job.dto.ProjectResponse;
import com.razorquake.job.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resumes/{resumeId}/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody @Valid ProjectRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        projectService.createProject(
                                candidateId,
                                resumeId,
                                request
                        )
                );
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getProjects(
            @PathVariable Long resumeId
    ) {
        return ResponseEntity.ok(
                projectService.getProjectsByResumeId(resumeId)
        );
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> updateProject(
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId,
            @PathVariable Long projectId,
            @RequestBody @Valid ProjectRequest request
            ) {
        return ResponseEntity.ok(
                projectService.updateProject(
                        candidateId,
                        resumeId,
                        projectId,
                        request
                )
        );
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<ApiResponse> deleteProject(
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId,
            @PathVariable Long projectId
    ) {
        projectService.deleteProject(
                candidateId,
                resumeId,
                projectId
        );
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(
                        ApiResponse.builder()
                                .message("Project deleted successfully")
                                .success(true)
                                .build()
                );
    }
}
