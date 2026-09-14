package com.razorquake.job.controller;

import com.razorquake.job.dto.ApiResponse;
import com.razorquake.job.dto.JobCategoryRequest;
import com.razorquake.job.dto.JobCategoryResponse;
import com.razorquake.job.service.JobCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-categories")
@RequiredArgsConstructor
public class JobCategoryController {
    private final JobCategoryService jobCategoryService;

    @PostMapping
    public ResponseEntity<JobCategoryResponse> createJobCategory(
            @RequestBody @Valid JobCategoryRequest jobCategoryRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(jobCategoryService.createJobCategory(jobCategoryRequest));
    }

    @GetMapping
    public ResponseEntity<List<JobCategoryResponse>> getAllJobCategories() {
        return ResponseEntity.ok(jobCategoryService.getAllJobCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobCategoryResponse> getJobCategoryById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(jobCategoryService.getJobCategoryById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobCategoryResponse> updateJobCategory(
            @PathVariable Long id,
            @RequestBody @Valid JobCategoryRequest jobCategoryRequest
    ) {
        return ResponseEntity.ok(jobCategoryService.updateJobCategory(id, jobCategoryRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteJobCategory(@PathVariable Long id) {
        jobCategoryService.deleteJobCategory(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(
                        ApiResponse.builder()
                                .message("Job category deleted successfully")
                                .success(true)
                                .build()
                );
    }

}
