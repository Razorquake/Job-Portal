package com.razorquake.job.controller;

import com.razorquake.job.dto.ApiResponse;
import com.razorquake.job.dto.JobTagRequest;
import com.razorquake.job.dto.JobTagResponse;
import com.razorquake.job.service.JobTagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-tags")
@RequiredArgsConstructor
public class JobTagController {

    private final JobTagService jobTagService;

    @PostMapping
    public ResponseEntity<JobTagResponse> createJobTag(
            @RequestBody @Valid JobTagRequest jobTagRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobTagService.createJobTag(jobTagRequest));
    }

    @GetMapping
    public ResponseEntity<List<JobTagResponse>> getAllJobTags() {
        return ResponseEntity.ok(jobTagService.getAllJobTags());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobTagResponse> getJobTagById(@PathVariable Long id) {
        return ResponseEntity.ok(jobTagService.getJobTagById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteJobTag(@PathVariable Long id) {
        jobTagService.deleteJobTagById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ApiResponse("Job tag deleted successfully", true));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobTagResponse> updateJobTag(
            @PathVariable Long id,
            @RequestBody @Valid JobTagRequest jobTagRequest
    ) {
        return ResponseEntity.ok(jobTagService.updateJobTagById(id, jobTagRequest));
    }
}
