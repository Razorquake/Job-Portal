package com.razorquake.job.service.impl;

import com.razorquake.job.dto.JobCategoryRequest;
import com.razorquake.job.dto.JobCategoryResponse;
import com.razorquake.job.exception.JobCategoryAlreadyExistsException;
import com.razorquake.job.exception.JobCategoryNotFoundException;
import com.razorquake.job.mapper.JobCategoryMapper;
import com.razorquake.job.model.JobCategory;
import com.razorquake.job.repository.JobCategoryRepository;
import com.razorquake.job.service.JobCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobCategoryServiceImpl implements JobCategoryService {

    private final JobCategoryRepository jobCategoryRepository;
    @Override
    public JobCategoryResponse createJobCategory(JobCategoryRequest jobCategoryRequest) {
        
        if (jobCategoryRepository.existsByName(jobCategoryRequest.getName()))
            throw new JobCategoryNotFoundException("Job category with name " + jobCategoryRequest.getName() + " already exists");
        
        JobCategory parent = null;
        if (jobCategoryRequest.getParentId() != null)
            parent = findJobCategoryById(jobCategoryRequest.getParentId());

        JobCategory jobCategory = JobCategoryMapper.toJobCategory(
                jobCategoryRequest,
                parent,
                generateUniqueSlug(jobCategoryRequest.getName())
        );

        return JobCategoryMapper.toJobCategoryResponse(
                jobCategoryRepository.save(jobCategory),
                true
        );
    }

    private String generateUniqueSlug(String name) {
        String base = name.toLowerCase().replaceAll("[^a-z0-9\\s-]", "")
                .trim().replaceAll("[\\s-]+", "-");

        String slug = base;
        int counter = 1;
        while (jobCategoryRepository.existsBySlug(slug)) {
            slug = base + "-" + counter++;
        }
        return slug;
    }

    @Override
    public List<JobCategoryResponse> getAllJobCategories() {
        return jobCategoryRepository
                .findAllByActiveTrue()
                .stream()
                .map(jobCategory -> JobCategoryMapper.toJobCategoryResponse(jobCategory, false))
                .toList();
    }

    @Override
    public JobCategoryResponse getJobCategoryById(Long id) {
        return JobCategoryMapper.toJobCategoryResponse(findJobCategoryById(id), true);
    }

    @Override
    public JobCategoryResponse updateJobCategory(Long id, JobCategoryRequest jobCategoryRequest) {
        JobCategory jobCategory = findJobCategoryById(id);

        if (!jobCategoryRequest.getName().equals(jobCategory.getName()) &&
                jobCategoryRepository.existsByName(jobCategoryRequest.getName())) {
            throw new JobCategoryAlreadyExistsException("Job category already exists");
        }

        if (jobCategoryRequest.getParentId() != null) {
            if (jobCategoryRequest.getParentId().equals(id)) {
                throw new JobCategoryAlreadyExistsException("Job category parent id cannot be itself");
            }
            jobCategory.setParent(findJobCategoryById(jobCategoryRequest.getParentId()));
        }

        jobCategory.setName(jobCategoryRequest.getName());
        jobCategory.setDescription(jobCategoryRequest.getDescription());
        jobCategory.setIconUrl(jobCategoryRequest.getIconUrl());

        return JobCategoryMapper.toJobCategoryResponse(
                jobCategoryRepository.save(jobCategory),
                true
        );
    }

    @Override
    public void deleteJobCategory(Long id) {
        JobCategory jobCategory = findJobCategoryById(id);
        jobCategory.setActive(false);
        jobCategoryRepository.save(jobCategory);
    }

    private JobCategory findJobCategoryById(Long id) {
        return jobCategoryRepository
                .findById(id)
                .orElseThrow(() -> new JobCategoryNotFoundException("Job category not found"));
    }
}
