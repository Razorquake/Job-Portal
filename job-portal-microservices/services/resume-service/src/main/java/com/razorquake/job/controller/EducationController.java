package com.razorquake.job.controller;

import com.razorquake.job.dto.ApiResponse;
import com.razorquake.job.dto.EducationRequest;
import com.razorquake.job.dto.EducationResponse;
import com.razorquake.job.service.EducationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resumes/{resumeId}/educations")
@RequiredArgsConstructor
public class EducationController {

    private final EducationService educationService;

    @PostMapping
    public ResponseEntity<EducationResponse> addEducation(
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody @Valid EducationRequest educationRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(educationService.addEducation(resumeId, candidateId, educationRequest));
    }

    @GetMapping
    public ResponseEntity<List<EducationResponse>> getEducation(
            @PathVariable Long resumeId
    ) {
        return ResponseEntity.ok(educationService.getEducationsByResumeId(resumeId));
    }

    @PutMapping("/{educationId}")
    public ResponseEntity<EducationResponse> updateEducation(
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId,
            @PathVariable Long educationId,
            @RequestBody @Valid EducationRequest educationRequest
    ) {
        return ResponseEntity.ok(educationService.updateEducation(resumeId, candidateId, educationId, educationRequest));
    }

    @DeleteMapping("/{educationId}")
    public ResponseEntity<ApiResponse> deleteEducation(
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId,
            @PathVariable Long educationId
    ) {
        educationService.deleteEducation(resumeId, candidateId, educationId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(
                        ApiResponse.builder()
                                .success(true)
                                .message("Education deleted successfully")
                                .build()
                );
    }
}
