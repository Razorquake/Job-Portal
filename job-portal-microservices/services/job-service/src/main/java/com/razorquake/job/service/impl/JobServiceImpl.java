package com.razorquake.job.service.impl;

import com.razorquake.job.domain.JobStatus;
import com.razorquake.job.dto.JobRequest;
import com.razorquake.job.dto.JobResponse;
import com.razorquake.job.exception.NotFoundException;
import com.razorquake.job.mapper.JobMapper;
import com.razorquake.job.model.Job;
import com.razorquake.job.model.JobCategory;
import com.razorquake.job.model.JobSkill;
import com.razorquake.job.model.JobTag;
import com.razorquake.job.model.embeddable.JobLocation;
import com.razorquake.job.model.embeddable.SalaryRange;
import com.razorquake.job.payload.JobSearchRequest;
import com.razorquake.job.repository.JobRepository;
import com.razorquake.job.repository.specification.JobSpecification;
import com.razorquake.job.service.JobCategoryService;
import com.razorquake.job.service.JobService;
import com.razorquake.job.service.JobSkillService;
import com.razorquake.job.service.JobTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final JobCategoryService jobCategoryService;
    private final JobSkillService jobSkillService;
    private final JobTagService jobTagService;

    @Override
    public JobResponse createJob(Long employerId, JobRequest jobRequest) {
        JobCategory jobCategory = jobCategoryService.findJobCategoryById(jobRequest.getCategoryId());
        Set<JobSkill> jobSkillSet =
                jobRequest.getSkillIds() == null ?
                        new HashSet<>() :
                        jobSkillService.getJobSkillsByIds(jobRequest.getSkillIds());

        Set<JobTag> jobTagSet =
                jobRequest.getTagIds() == null ?
                        new HashSet<>() :
                        jobTagService.getJobTagsByIds(jobRequest.getTagIds());
        return JobMapper.toJobResponse(
                jobRepository.save(
                        JobMapper.toJob(
                                jobRequest,
                                employerId,
                                jobCategory,
                                jobSkillSet,
                                jobTagSet
                        )
                )
        );
    }

    @Override
    public JobResponse getJobById(Long id) {
        return JobMapper.toJobResponse(jobRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Job not found")
        ));
    }

    @Override
    public List<JobResponse> getJobs(JobSearchRequest jobSearchRequest) {
        return jobRepository.findAll(JobSpecification.buildSpecification(jobSearchRequest))
                .stream().map(JobMapper::toJobResponse).toList();
    }

    @Override
    public List<JobResponse> getJobsByCompany(Long companyId) {
        return jobRepository.findAllByCompanyId(companyId)
                .stream().map(JobMapper::toJobResponse).toList();
    }

    @Override
    public JobResponse updateJob(Long jobId, Long employerId, JobRequest jobRequest) {
        assertEmployerId(employerId, jobId);
        Job job = jobRepository.findById(jobId).orElseThrow(
                () -> new NotFoundException("Job not found")
        );
        job.setTitle(jobRequest.getTitle());
        job.setDescription(jobRequest.getDescription());
        job.setRequirements(jobRequest.getRequirements());
        job.setResponsibilities(jobRequest.getResponsibilities());
        job.setBenefits(jobRequest.getBenefits());
        job.setCategory(
                jobCategoryService
                        .findJobCategoryById(
                                jobRequest.getCategoryId()
                        )
        );
        job.setSkills(
                jobRequest.getSkillIds() == null ?
                new HashSet<>() :
                jobSkillService.getJobSkillsByIds(jobRequest.getSkillIds())
        );
        job.setTags(
                jobRequest.getTagIds() == null ?
                new HashSet<>() :
                jobTagService.getJobTagsByIds(jobRequest.getTagIds())
        );
        job.setLocation(JobLocation.builder()
                        .address(jobRequest.getAddress())
                        .city(jobRequest.getCity())
                        .state(jobRequest.getState())
                        .country(jobRequest.getCountry())
                        .zipCode(jobRequest.getZipCode())
                .build());
        job.setSalaryRange(
                SalaryRange.builder()
                        .minSalary(jobRequest.getMinSalary())
                        .maxSalary(jobRequest.getMaxSalary())
                .build()
        );
        job.setJobType(jobRequest.getJobType());
        job.setWorkMode(jobRequest.getWorkMode());
        job.setExperienceLevel(jobRequest.getExperienceLevel());
        job.setOpenings(jobRequest.getOpenings() == null ? job.getOpenings() : jobRequest.getOpenings());
        job.setApplicationDeadline(jobRequest.getApplicationDeadline());
        job.setExpiresAt(jobRequest.getExpiresAt());

        return JobMapper.toJobResponse(
                jobRepository.save(job)
        );
    }

    @Override
    public JobResponse publishJob(Long jobId, Long employerId) {
        assertEmployerId(employerId, jobId);
        Job job = jobRepository.findById(jobId).orElseThrow(
                () -> new NotFoundException("Job not found")
        );
        if (job.getStatus() == JobStatus.CLOSED || job.getStatus() == JobStatus.EXPIRED)
            throw new RuntimeException("Job is already closed or expired");
        job.setStatus(JobStatus.OPEN);
        job.setPublishedAt(LocalDateTime.now());
        job.setActive(true);
        return JobMapper.toJobResponse(jobRepository.save(job));
    }

    private void assertEmployerId(Long employerId, Long jobId) {
        if (!jobRepository.existsByIdAndEmployerId(jobId, employerId)) {
            throw new NotFoundException("Job not found");
        }
    }

    @Override
    public JobResponse closeJob(Long jobId, Long employerId) {
        assertEmployerId(employerId, jobId);
        Job job = jobRepository.findById(jobId).orElseThrow(
                () -> new NotFoundException("Job not found")
        );
        job.setStatus(JobStatus.CLOSED);
        job.setClosedAt(LocalDateTime.now());
        job.setActive(false);
        return JobMapper.toJobResponse(jobRepository.save(job));
    }

    @Override
    public void deleteJob(Long jobId, Long employerId) {
        assertEmployerId(employerId, jobId);
        jobRepository.deleteById(jobId);
    }

    @Override
    public List<JobResponse> getAllJobsAdmin() {
        return jobRepository.findAll().stream()
                .map(JobMapper::toJobResponse)
                .collect(Collectors.toList());
    }
}
