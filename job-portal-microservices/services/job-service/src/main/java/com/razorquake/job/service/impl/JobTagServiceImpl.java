package com.razorquake.job.service.impl;

import com.razorquake.job.dto.JobTagRequest;
import com.razorquake.job.dto.JobTagResponse;
import com.razorquake.job.exception.AlreadyExistsException;
import com.razorquake.job.exception.NotFoundException;
import com.razorquake.job.mapper.JobTagMapper;
import com.razorquake.job.model.JobTag;
import com.razorquake.job.repository.JobTagRepository;
import com.razorquake.job.service.JobTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class JobTagServiceImpl implements JobTagService {

    private final JobTagRepository jobTagRepository;

    @Override
    public JobTagResponse createJobTag(JobTagRequest jobTagRequest) {

        if (jobTagRepository.existsByName(jobTagRequest.getName())) {
            throw  new NotFoundException("Job tag already exists with name: " + jobTagRequest.getName());
        }
        return JobTagMapper.mapToJobTagResponse(
                jobTagRepository.save(
                        JobTagMapper.mapToJobTagEntity(
                                jobTagRequest,
                                generateUniqueSlug(jobTagRequest.getName())
                        )
                )
        );
    }

    private JobTag getJobTagEntityById(Long id) {
        return jobTagRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("Job tag not found with id: " + id)
                );
    }

    private String generateUniqueSlug(String name) {
        String base = name.toLowerCase().replaceAll("[^a-z0-9\\s-]", "")
                .trim().replaceAll("[\\s-]+", "-");

        String slug = base;
        int counter = 1;
        while (jobTagRepository.existsBySlug(slug)) {
            slug = base + "-" + counter++;
        }
        return slug;
    }

    @Override
    public List<JobTagResponse> getAllJobTags() {
        return jobTagRepository.findAll().stream()
                .map(JobTagMapper::mapToJobTagResponse)
                .toList();
    }

    @Override
    public JobTagResponse getJobTagById(Long id) {
        return JobTagMapper.mapToJobTagResponse(
                getJobTagEntityById(id)
        );
    }

    @Override
    public void deleteJobTagById(Long id) {
        jobTagRepository.deleteById(id);
    }

    @Override
    public JobTagResponse updateJobTagById(Long id, JobTagRequest jobTagRequest) {
        JobTag jobTag = getJobTagEntityById(id);
        if (!jobTagRequest.getName().equals(jobTag.getName()) &&
        jobTagRepository.existsByName(jobTagRequest.getName()))
            throw new AlreadyExistsException("Job tag with name: " + jobTagRequest.getName() + " already exists");
        jobTag.setName(jobTagRequest.getName());
        return JobTagMapper.mapToJobTagResponse(jobTagRepository.save(jobTag));
    }

    @Override
    public Set<JobTag> getJobTagsByIds(Set<Long> jobTagIds) {
        return Set.copyOf(jobTagRepository.findAllById(jobTagIds));
    }
}
