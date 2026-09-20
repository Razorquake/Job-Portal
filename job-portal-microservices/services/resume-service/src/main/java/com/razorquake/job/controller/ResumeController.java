package com.razorquake.job.controller;

import com.razorquake.job.dto.ApiResponse;
import com.razorquake.job.dto.CreateResumeRequest;
import com.razorquake.job.dto.PersonalInfoResponse;
import com.razorquake.job.dto.ResumeResponse;
import com.razorquake.job.service.ResumeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping
    public ResponseEntity<ResumeResponse> createResume(
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody @Valid CreateResumeRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resumeService.createResume(candidateId, request));
    }

    @GetMapping("/{resumeId}")
    public ResponseEntity<ResumeResponse> getResume(
            @RequestHeader("X-User-Id") Long candidateId,
            @PathVariable Long resumeId
    ) {
        return ResponseEntity.ok(resumeService.getResume(candidateId, resumeId));
    }

    @GetMapping("/my")
    public ResponseEntity<List<ResumeResponse>> getResumes(
            @RequestHeader("X-User-Id") Long candidateId
    ) {
        return ResponseEntity.ok(resumeService.getResumes(candidateId));
    }

    @PutMapping("/{resumeId}/personal-info")
    public ResponseEntity<ResumeResponse> updatePersonalInfo(
            @RequestHeader("X-User-Id") Long candidateId,
            @PathVariable Long resumeId,
            @RequestBody @Valid PersonalInfoResponse request
    ) {
        return ResponseEntity.ok(resumeService.updatePersonalInfo(candidateId, resumeId, request));
    }

    @PatchMapping("/{resumeId}/summary")
    public ResponseEntity<ResumeResponse> updateSummary(
            @RequestHeader("X-User-Id") Long candidateId,
            @PathVariable Long resumeId,
            @RequestParam String summary
    ) {
        return ResponseEntity.ok(resumeService.updateSummary(candidateId, resumeId, summary));
    }

    @PatchMapping("/{resumeId}/set-default")
    public ResponseEntity<ResumeResponse> setDefaultResume(
            @RequestHeader("X-User-Id") Long candidateId,
            @PathVariable Long resumeId
    ) {
        return ResponseEntity.ok(resumeService.setDefaultResume(candidateId, resumeId));
    }

    @DeleteMapping("/{resumeId}")
    public ResponseEntity<ApiResponse> deleteResume(
            @RequestHeader("X-User-Id") Long candidateId,
            @PathVariable Long resumeId
    ) {
        resumeService.deleteResume(candidateId, resumeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ApiResponse(
                        "Resume deleted successfully",
                        true
                ));
    }

}
