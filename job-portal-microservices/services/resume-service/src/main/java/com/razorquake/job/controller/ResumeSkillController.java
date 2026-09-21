package com.razorquake.job.controller;

import com.razorquake.job.dto.ApiResponse;
import com.razorquake.job.dto.ResumeSkillRequest;
import com.razorquake.job.dto.ResumeSkillResponse;
import com.razorquake.job.service.ResumeSkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resumes/{resumeId}/skills")
@RequiredArgsConstructor
public class ResumeSkillController {

    private final ResumeSkillService resumeSkillService;


    @PostMapping
    public ResponseEntity<ResumeSkillResponse> createResumeSkill(
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestHeader @Valid ResumeSkillRequest resumeSkillRequest

            ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        resumeSkillService.addResumeSkill(
                                resumeId,
                                candidateId,
                                resumeSkillRequest
                        )
                );
    }


    @GetMapping
    public ResponseEntity<List<ResumeSkillResponse>> getResumeSkills(
            @PathVariable Long resumeId
    ) {
        return ResponseEntity.ok(
                resumeSkillService.getResumeSkillsByResumeId(resumeId)
        );
    }

    @PutMapping("/{skillId}")
    public ResponseEntity<ResumeSkillResponse> updateResumeSkill(
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId,
            @PathVariable Long skillId,
            @RequestHeader @Valid ResumeSkillRequest resumeSkillRequest
    ) {
        return ResponseEntity.ok(
                resumeSkillService.updateResumeSkill(resumeId, candidateId, skillId, resumeSkillRequest)
        );
    }

    @DeleteMapping("/{skillId}")
    public ResponseEntity<ApiResponse> deleteResumeSkill(
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId,
            @PathVariable Long skillId
    ) {
        resumeSkillService.deleteResumeSkill(resumeId, candidateId, skillId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(
                        ApiResponse.builder()
                                .message("Resume skill deleted successfully")
                                .success(true)
                                .build()
                );
    }


}
