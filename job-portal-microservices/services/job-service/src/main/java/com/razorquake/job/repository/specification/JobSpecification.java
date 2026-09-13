package com.razorquake.job.repository.specification;

import com.razorquake.job.domain.ExperienceLevel;
import com.razorquake.job.domain.JobStatus;
import com.razorquake.job.domain.JobType;
import com.razorquake.job.domain.WorkMode;
import com.razorquake.job.model.Job;
import com.razorquake.job.payload.JobSearchRequest;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class JobSpecification {

    private static Specification<Job> byKeyword(String keyword) {
        String pattern = "%" + keyword.toLowerCase() + "%";
        return (root, _, criteriaBuilder) ->
                criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), pattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), pattern)
                );
    }

    private static Specification<Job> byStatus(JobStatus status) {
        return (root, _, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }

    private static Specification<Job> byJobType(JobType jobType) {
        return (root, _, criteriaBuilder) -> criteriaBuilder.equal(root.get("jobType"), jobType);
    }

    private static Specification<Job> byWorkMode(WorkMode workMode) {
        return (root, _, criteriaBuilder) -> criteriaBuilder.equal(root.get("workMode"), workMode);
    }

    private static Specification<Job> byExperienceLevel(ExperienceLevel experienceLevel) {
        return (root, _, criteriaBuilder) -> criteriaBuilder.equal(root.get("experienceLevel"), experienceLevel);
    }

    private static Specification<Job> byCompanyId(Long companyId) {
        return (root, _, criteriaBuilder) -> criteriaBuilder.equal(root.get("companyId"), companyId);
    }

    private static Specification<Job> byCategoryId(Long categoryId) {
        return (root, _, criteriaBuilder) -> criteriaBuilder.equal(root.get("category").get("id"), categoryId);
    }

    private static Specification<Job> byLocation(String location) {
        String pattern = "%" + location.toLowerCase() + "%";
        return (root, _, cb) -> cb.or(
                cb.like(cb.lower(root.get("location").get("city")), pattern),
                cb.like(cb.lower(root.get("location").get("state")), pattern),
                cb.like(cb.lower(root.get("location").get("country")), pattern)
        );
    }

    private static Specification<Job> byMinSalary(BigDecimal minSalary) {
        return (root, _, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("salaryRange").get("maxSalary"), minSalary);
    }

    private static Specification<Job> byMinOpenings(Integer minOpenings) {
        return (root, _, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("openings"), minOpenings);
    }

    private static Specification<Job> byMaxOpenings(Integer maxOpenings) {
        return (root, _, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("openings"), maxOpenings);
    }

    private static Specification<Job> byMaxSalary(BigDecimal maxSalary) {
        return (root, _, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("salaryRange").get("minSalary"), maxSalary);
    }

    private static Specification<Job> matchAll() {
        return (_, _, criteriaBuilder) -> criteriaBuilder.conjunction();
    }

    private static Specification<Job> combine(Specification<Job> spec, Specification<Job> other) {
        if (spec == null) {
            return other;
        }
        if (other == null) {
            return spec;
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(spec.toPredicate(root, query, criteriaBuilder), other.toPredicate(root, query, criteriaBuilder));
    }

    public static Specification<Job> buildSpecification(JobSearchRequest jobSearchRequest) {
        Specification<Job> spec = matchAll();

        if (jobSearchRequest.getKeyword() != null) {
            spec = combine(spec, byKeyword(jobSearchRequest.getKeyword()));
        }

        spec = combine(spec, byStatus(
                jobSearchRequest.getJobStatus() != null ? jobSearchRequest.getJobStatus() : JobStatus.OPEN
        ));

        if (jobSearchRequest.getJobType() != null) {
            spec = combine(spec, byJobType(jobSearchRequest.getJobType()));
        }

        if (jobSearchRequest.getWorkMode() != null) {
            spec = combine(spec, byWorkMode(jobSearchRequest.getWorkMode()));
        }

        if (jobSearchRequest.getExperienceLevel() != null) {
            spec = combine(spec, byExperienceLevel(jobSearchRequest.getExperienceLevel()));
        }

        if (jobSearchRequest.getCompanyId() != null) {
            spec = combine(spec, byCompanyId(jobSearchRequest.getCompanyId()));
        }

        if (jobSearchRequest.getCategoryId() != null) {
            spec = combine(spec, byCategoryId(jobSearchRequest.getCategoryId()));
        }

        if (jobSearchRequest.getLocation() != null && !jobSearchRequest.getLocation().isBlank()) {
            spec = combine(spec, byLocation(jobSearchRequest.getLocation()));
        }

        if (jobSearchRequest.getMinSalary() != null) {
            spec = combine(spec, byMinSalary(jobSearchRequest.getMinSalary()));
        }

        if (jobSearchRequest.getMaxSalary() != null) {
            spec = combine(spec, byMaxSalary(jobSearchRequest.getMaxSalary()));
        }

        if (jobSearchRequest.getMinOpenings() != null) {
            spec = combine(spec, byMinOpenings(jobSearchRequest.getMinOpenings()));
        }

        if (jobSearchRequest.getMaxOpenings() != null) {
            spec = combine(spec, byMaxOpenings(jobSearchRequest.getMaxOpenings()));
        }

        return spec;
    }
}
