package com.razorquake.job.mapper;

import com.razorquake.job.domain.JobStatus;
import com.razorquake.job.dto.CompanyResponse;
import com.razorquake.job.dto.JobRequest;
import com.razorquake.job.dto.JobResponse;
import com.razorquake.job.model.Job;
import com.razorquake.job.model.JobCategory;
import com.razorquake.job.model.JobSkill;
import com.razorquake.job.model.JobTag;
import com.razorquake.job.model.embeddable.JobLocation;
import com.razorquake.job.model.embeddable.SalaryRange;

import java.util.Set;
import java.util.stream.Collectors;

public class JobMapper {

    public static Job toJob(JobRequest jobRequest, Long employerId, JobCategory jobCategory, Set<JobSkill> jobSkillSet, Set<JobTag> jobTagSet) {
        Long companyId = 1L;
        return Job.builder()
                .title(jobRequest.getTitle())
                .description(jobRequest.getDescription())
                .requirements(jobRequest.getRequirements())
                .responsibilities(jobRequest.getResponsibilities())
                .benefits(jobRequest.getBenefits())
                .companyId(companyId)
                .employerId(employerId)
                .category(jobCategory)
                .skills(jobSkillSet)
                .tags(jobTagSet)
                .location(
                        JobLocation.builder()
                                .address(jobRequest.getAddress())
                                .city(jobRequest.getCity())
                                .state(jobRequest.getState())
                                .country(jobRequest.getCountry())
                                .zipCode(jobRequest.getZipCode())
                                .build()
                )
                .salaryRange(
                        SalaryRange.builder()
                                .minSalary(jobRequest.getMinSalary())
                                .maxSalary(jobRequest.getMaxSalary())
                                .build()
                )
                .jobType(jobRequest.getJobType())
                .workMode(jobRequest.getWorkMode())
                .experienceLevel(jobRequest.getExperienceLevel())
                .openings(jobRequest.getOpenings() == null ? 1 : jobRequest.getOpenings())
                .applicationDeadline(jobRequest.getApplicationDeadline())
                .expiresAt(jobRequest.getExpiresAt())
                .active(true)
                .status(JobStatus.DRAFT)
                .build();
    }

    public static JobResponse toJobResponse(Job job) {
        CompanyResponse companyResponse = CompanyResponse.builder()
                .id(job.getCompanyId())
                .build();
        return JobResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .requirements(job.getRequirements())
                .responsibilities(job.getResponsibilities())
                .benefits(job.getBenefits())
                .employerId(job.getEmployerId())
                .category(JobCategoryMapper.toJobCategoryResponse(job.getCategory(), false))
                .skills(job.getSkills().stream().map(JobSkillMapper::toResponse).collect(Collectors.toSet()))
                .tags(job.getTags().stream().map(JobTagMapper::mapToJobTagResponse).collect(Collectors.toSet()))
                .company(companyResponse)
                .address(job.getLocation().getAddress())
                .city(job.getLocation().getCity())
                .state(job.getLocation().getState())
                .country(job.getLocation().getCountry())
                .zipCode(job.getLocation().getZipCode())
                .minSalary(job.getSalaryRange().getMinSalary())
                .maxSalary(job.getSalaryRange().getMaxSalary())
                .jobType(job.getJobType())
                .workMode(job.getWorkMode())
                .experienceLevel(job.getExperienceLevel())
                .status(job.getStatus())
                .openings(job.getOpenings())
                .applicationDeadline(job.getApplicationDeadline())
                .expiresAt(job.getExpiresAt())
                .active(job.getActive())
                .createdAt(job.getCreatedAt())
                .updatedAt(job.getUpdatedAt())
                .publishedAt(job.getPublishedAt())
                .closedAt(job.getClosedAt())
                .active(job.getActive())
                .build();
    }

}
