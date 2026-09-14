package com.razorquake.job.controller;

import com.razorquake.job.dto.ApiResponse;
import com.razorquake.job.dto.JobSkillRequest;
import com.razorquake.job.dto.JobSkillResponse;
import com.razorquake.job.service.JobSkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-skills")
@RequiredArgsConstructor
public class JobSkillController {

    private final JobSkillService jobSkillService;

    @PostMapping
    public ResponseEntity<JobSkillResponse> createJobSkill(
            @RequestBody @Valid JobSkillRequest jobSkillRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(jobSkillService.createJobSkill(jobSkillRequest));
    }

    @GetMapping
    public ResponseEntity<List<JobSkillResponse>> getAllJobSkills() {
        return ResponseEntity.ok(jobSkillService.getAllJobSkills());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobSkillResponse> getJobSkillById(@PathVariable Long id) {
        return ResponseEntity.ok(jobSkillService.getJobSkillById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobSkillResponse> updateJobSkill(
            @PathVariable Long id,
            @RequestBody @Valid JobSkillRequest jobSkillRequest
    ) {
        return ResponseEntity.ok(jobSkillService.updateJobSkill(id, jobSkillRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteJobSkill(@PathVariable Long id) {
        jobSkillService.deleteJobSkill(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(
                        ApiResponse.builder()
                                .message("Job skill deleted successfully")
                                .success(true)
                                .build()
                );
    }
}
