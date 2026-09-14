package com.razorquake.job.service;

import com.razorquake.job.dto.JobCategoryRequest;
import com.razorquake.job.dto.JobCategoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JobCategoryService {

    JobCategoryResponse createJobCategory(JobCategoryRequest jobCategoryRequest);

    List<JobCategoryResponse> getAllJobCategories();

    JobCategoryResponse getJobCategoryById(Long id);

    JobCategoryResponse updateJobCategory(Long id, JobCategoryRequest jobCategoryRequest);

    void deleteJobCategory(Long id);


}
