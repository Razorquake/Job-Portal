package com.razorquake.job.service.impl;

import com.razorquake.job.dto.WorkExperienceRequest;
import com.razorquake.job.dto.WorkExperienceResponse;
import com.razorquake.job.exception.NotFoundException;
import com.razorquake.job.exception.UnauthorizedException;
import com.razorquake.job.mapper.WorkExperienceMapper;
import com.razorquake.job.model.Resume;
import com.razorquake.job.model.WorkExperience;
import com.razorquake.job.repository.WorkExperienceRepository;
import com.razorquake.job.service.ResumeService;
import com.razorquake.job.service.WorkExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkExperienceServiceImpl implements WorkExperienceService {

    private final ResumeService resumeService;
    private final WorkExperienceRepository workExperienceRepository;

    @Override
    public WorkExperienceResponse addWorkExperience(Long resumeId, Long candidateId, WorkExperienceRequest request) {
        Resume resume = resumeService.getResumeEntity(resumeId, candidateId);
        return WorkExperienceMapper.toWorkExperienceResponse(
                workExperienceRepository.save(
                        WorkExperienceMapper.toWorkExperience(
                                request,
                                resume
                        )
                )
        );

    }

    @Override
    public List<WorkExperienceResponse> getWorkExperiencesByResumeId(Long resumeId) {
        return workExperienceRepository.findByResumeIdOrderByDisplayOrderAsc(resumeId).stream()
                .map(WorkExperienceMapper::toWorkExperienceResponse)
                .toList();
    }

    @Override
    public WorkExperienceResponse updateWorkExperience(
            Long resumeId,
            Long candidateId,
            Long workExperienceId,

            WorkExperienceRequest request
    ) {
        WorkExperience workExperience = workExperienceRepository.findById(workExperienceId)
                .orElseThrow(() -> new NotFoundException("Work experience with id " + workExperienceId + " not found"));
        if (!workExperience.getResume().getId().equals(resumeId)) {
            throw new UnauthorizedException(
                    "Work experience with id " + workExperienceId +
                            " does not belong to resume with id " + resumeId
            );
        }
        resumeService.getResumeEntity(resumeId, candidateId);
        workExperience.setCompanyName(request.getCompanyName());
        workExperience.setCompanyLogoUrl(request.getCompanyLogoUrl());
        workExperience.setJobTitle(request.getJobTitle());
        workExperience.setEmploymentType(request.getEmploymentType());
        workExperience.setLocation(request.getLocation());
        workExperience.setStartDate(request.getStartDate());
        workExperience.setEndDate(request.getEndDate());
        workExperience.setIsCurrentJob(request.getIsCurrentJob());
        workExperience.setDescription(request.getDescription());
        workExperience.setTechnologies(
                request.getTechnologies() != null ? request.getTechnologies() : workExperience.getTechnologies()
        );
        workExperience.setDisplayOrder(
                request.getDisplayOrder() != null ? request.getDisplayOrder() : workExperience.getDisplayOrder()
        );
        return WorkExperienceMapper.toWorkExperienceResponse(
                workExperienceRepository.save(workExperience)
        );
    }

    @Override
    public void deleteWorkExperience(Long resumeId, Long candidateId, Long workExperienceId) {
        WorkExperience workExperience = workExperienceRepository.findById(workExperienceId)
                .orElseThrow(() -> new NotFoundException("Work experience with id " + workExperienceId + " not found"));
        if (!workExperience.getResume().getId().equals(resumeId)) {
            throw new UnauthorizedException(
                    "Work experience with id " + workExperienceId +
                            " does not belong to resume with id " + resumeId
            );
        }
        resumeService.getResumeEntity(resumeId, candidateId);
        workExperienceRepository.delete(workExperience);
    }
}
