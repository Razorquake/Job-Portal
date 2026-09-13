package com.razorquake.job.service;

import com.razorquake.job.dto.JobRequest;
import com.razorquake.job.dto.JobResponse;
import com.razorquake.job.payload.JobSearchRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JobService {

    JobResponse createJob(Long employerId, JobRequest jobRequest);

    JobResponse getJobById(Long id);

    List<JobResponse> getJobs(JobSearchRequest jobSearchRequest);

    List<JobResponse> getJobsByCompany(Long companyId);

    JobResponse updateJob(Long jobId, Long employerId, JobRequest jobRequest);

    JobResponse publishJob(Long jobId, Long employerId);

    JobResponse closeJob(Long jobId, Long employerId);

    void deleteJob(Long jobId, Long employerId);

    List<JobResponse> getAllJobsAdmin();
}
