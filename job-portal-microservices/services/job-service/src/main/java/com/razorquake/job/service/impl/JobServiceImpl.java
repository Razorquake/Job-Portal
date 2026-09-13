package com.razorquake.job.service.impl;

import com.razorquake.job.domain.JobStatus;
import com.razorquake.job.dto.JobRequest;
import com.razorquake.job.dto.JobResponse;
import com.razorquake.job.exception.JobNotFoundException;
import com.razorquake.job.mapper.JobMapper;
import com.razorquake.job.model.Job;
import com.razorquake.job.model.embeddable.JobLocation;
import com.razorquake.job.model.embeddable.SalaryRange;
import com.razorquake.job.payload.JobSearchRequest;
import com.razorquake.job.repository.JobRepository;
import com.razorquake.job.repository.specification.JobSpecification;
import com.razorquake.job.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    @Override
    public JobResponse createJob(Long employerId, JobRequest jobRequest) {
        return JobMapper.toJobResponse(jobRepository.save(JobMapper.toJob(jobRequest, employerId)));
    }

    @Override
    public JobResponse getJobById(Long id) {
        return JobMapper.toJobResponse(jobRepository.findById(id).orElseThrow(
                () -> new JobNotFoundException("Job not found")
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
                () -> new JobNotFoundException("Job not found")
        );
        job.setTitle(jobRequest.getTitle());
        job.setDescription(jobRequest.getDescription());
        job.setRequirements(jobRequest.getRequirements());
        job.setResponsibilities(jobRequest.getResponsibilities());
        job.setBenefits(jobRequest.getBenefits());
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
                () -> new JobNotFoundException("Job not found")
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
            throw new JobNotFoundException("Job not found");
        }
    }

    @Override
    public JobResponse closeJob(Long jobId, Long employerId) {
        assertEmployerId(employerId, jobId);
        Job job = jobRepository.findById(jobId).orElseThrow(
                () -> new JobNotFoundException("Job not found")
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
