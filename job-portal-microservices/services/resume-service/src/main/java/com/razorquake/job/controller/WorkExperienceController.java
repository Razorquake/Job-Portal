package com.razorquake.job.controller;

import com.razorquake.job.dto.ApiResponse;
import com.razorquake.job.dto.WorkExperienceRequest;
import com.razorquake.job.dto.WorkExperienceResponse;
import com.razorquake.job.service.WorkExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resume/{resumeId}/work-experiences")
@RequiredArgsConstructor
public class WorkExperienceController {

    private final WorkExperienceService workExperienceService;

    @PostMapping
    public ResponseEntity<WorkExperienceResponse> createWorkExperience(
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody WorkExperienceRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(workExperienceService.addWorkExperience(resumeId, candidateId, request));
    }

    @GetMapping
    public ResponseEntity<List<WorkExperienceResponse>> getWorkExperiences(
            @PathVariable Long resumeId
    ) {
        return ResponseEntity.ok(workExperienceService.getWorkExperiencesByResumeId(resumeId));
    }

    @PutMapping("/{workExperienceId}")
    public ResponseEntity<WorkExperienceResponse> updateWorkExperience(
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId,
            @PathVariable Long workExperienceId,
            @RequestBody WorkExperienceRequest request
    ) {
        return ResponseEntity.ok(
                workExperienceService.updateWorkExperience(
                        resumeId,
                        candidateId,
                        workExperienceId,
                        request
                )
        );
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse> deleteWorkExperience(
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestParam Long workExperienceId
    ) {
        workExperienceService.deleteWorkExperience(resumeId, candidateId, workExperienceId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(
                        ApiResponse.builder()
                                .message("Work experience deleted successfully")
                                .success(true)
                                .build()
                );
    }
}
