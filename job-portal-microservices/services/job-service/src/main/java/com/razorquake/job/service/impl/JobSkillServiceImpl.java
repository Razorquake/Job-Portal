package com.razorquake.job.service.impl;

import com.razorquake.job.dto.JobSkillRequest;
import com.razorquake.job.dto.JobSkillResponse;
import com.razorquake.job.mapper.JobSkillMapper;
import com.razorquake.job.model.JobSkill;
import com.razorquake.job.repository.JobSkillRepository;
import com.razorquake.job.service.JobSkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class JobSkillServiceImpl implements JobSkillService {

    private final JobSkillRepository jobSkillRepository;

    @Override
    public JobSkillResponse createJobSkill(JobSkillRequest jobSkillRequest) {
        if (jobSkillRepository.existsByName(jobSkillRequest.getName()))
            throw new IllegalArgumentException("Job skill with name " + jobSkillRequest.getName() + " already exists");
        return JobSkillMapper.toResponse(
                jobSkillRepository.save(
                        JobSkillMapper.toEntity(
                                jobSkillRequest,
                                generateUniqueSlug(jobSkillRequest.getName())
                        )
                )
        );
    }

    private String generateUniqueSlug(String name) {
        String base = name.toLowerCase().replaceAll("[^a-z0-9\\s-]", "")
                .trim().replaceAll("[\\s-]+", "-");

        String slug = base;
        int counter = 1;
        while (jobSkillRepository.existsBySlug(slug)) {
            slug = base + "-" + counter++;
        }
        return slug;
    }

    @Override
    public List<JobSkillResponse> getAllJobSkills() {
        return jobSkillRepository.findAllByActiveTrue().stream()
                .map(JobSkillMapper::toResponse)
                .toList();
    }

    @Override
    public JobSkillResponse getJobSkillById(Long id) {
        return JobSkillMapper.toResponse(
                jobSkillRepository
                        .findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Job skill with id " + id + " not found"))
        );
    }

    @Override
    public JobSkillResponse updateJobSkill(Long id, JobSkillRequest jobSkillRequest) {
        JobSkill existingSkill = jobSkillRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Job skill with id " + id + " not found"));
        if (!existingSkill.getName().equals(jobSkillRequest.getName()) &&
                jobSkillRepository.existsByName(jobSkillRequest.getName()))
            throw new IllegalArgumentException("Job skill with name "
                    + jobSkillRequest.getName()
                    + " already exists");

        existingSkill.setName(jobSkillRequest.getName());
        existingSkill.setCategory(jobSkillRequest.getCategory());
        return JobSkillMapper.toResponse(
                jobSkillRepository.save(existingSkill)
        );
    }

    @Override
    public void deleteJobSkill(Long id) {
        JobSkill existingSkill = jobSkillRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Job skill with id " + id + " not found"));
        existingSkill.setActive(false);
    }

    @Override
    public Set<JobSkill> getJobSkillsByIds(Set<Long> ids) {
        return new HashSet<>(jobSkillRepository.findAllById(ids));
    }
}
