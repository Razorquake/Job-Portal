package com.razorquake.job.controller;

import com.razorquake.job.dto.ApiResponse;
import com.razorquake.job.dto.JobRequest;
import com.razorquake.job.dto.JobResponse;
import com.razorquake.job.payload.JobSearchRequest;
import com.razorquake.job.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @PostMapping
    public ResponseEntity<JobResponse> createJob(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody @Valid JobRequest jobRequest
            ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(jobService.createJob(userId, jobRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJobById(
            @PathVariable Long id
            ) {
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    @GetMapping
    public ResponseEntity<List<JobResponse>> getAllJobs(
            @ModelAttribute JobSearchRequest jobRequest
            ) {
        return ResponseEntity.ok(jobService.getJobs(jobRequest));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<JobResponse>> getJobsByCompanyId(
            @PathVariable Long companyId
            ) {
        return ResponseEntity.ok(jobService.getJobsByCompany(companyId));
    }

    @GetMapping("/admin")
    public ResponseEntity<List<JobResponse>> getAllJobsByAdmin() {
        return ResponseEntity.ok(jobService.getAllJobsAdmin());
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobResponse> updateJob(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody @Valid JobRequest jobRequest
            ) {
        return ResponseEntity.ok(jobService.updateJob(id, userId, jobRequest));
    }

    @PatchMapping("/{id}/publish")
    public ResponseEntity<JobResponse> publishJob(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId
            ) {
        return ResponseEntity.ok(jobService.publishJob(id, userId));
    }

    @PatchMapping("/{id}/close")
    public ResponseEntity<JobResponse> closeJob(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId
            ) {
        return ResponseEntity.ok(jobService.closeJob(id, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteJob(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId
            ) {
        jobService.deleteJob(id, userId);
        return ResponseEntity.ok(new ApiResponse("Job deleted successfully", true));
    }



}
