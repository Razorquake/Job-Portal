package com.razorquake.job.repository;

import com.razorquake.job.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {

    List<Job> findAllByCompanyId(Long companyId);

    boolean existsByIdAndEmployerId(Long id, Long employerId);
}
