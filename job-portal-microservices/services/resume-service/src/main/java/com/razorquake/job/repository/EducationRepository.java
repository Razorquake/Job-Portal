package com.razorquake.job.repository;

import com.razorquake.job.model.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {

    List<Education> findByResume_IdOrderByDisplayOrderAsc(Long resumeId);
}
