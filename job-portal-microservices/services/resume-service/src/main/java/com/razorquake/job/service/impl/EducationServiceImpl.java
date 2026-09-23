package com.razorquake.job.service.impl;

import com.razorquake.job.dto.EducationRequest;
import com.razorquake.job.dto.EducationResponse;
import com.razorquake.job.exception.NotFoundException;
import com.razorquake.job.exception.UnauthorizedException;
import com.razorquake.job.mapper.EducationMapper;
import com.razorquake.job.model.Education;
import com.razorquake.job.model.Resume;
import com.razorquake.job.repository.EducationRepository;
import com.razorquake.job.service.EducationService;
import com.razorquake.job.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationServiceImpl implements EducationService {

    private final EducationRepository educationRepository;
    private final ResumeService resumeService;

    @Override
    public EducationResponse addEducation(Long resumeId, Long candidateId, EducationRequest educationRequest) {
        Resume resume = resumeService.getResumeEntity(resumeId, candidateId);

        return EducationMapper.toEducationResponse(
                educationRepository.save(
                        EducationMapper.toEntity(
                                educationRequest,
                                resume
                        )
                )
        );
    }

    @Override
    public List<EducationResponse> getEducationsByResumeId(Long resumeId) {
        return educationRepository
                .findByResume_IdOrderByDisplayOrderAsc(resumeId)
                .stream()
                .map(EducationMapper::toEducationResponse)
                .toList();
    }

    @Override
    public EducationResponse updateEducation(
            Long resumeId,
            Long candidateId,
            Long educationId,
            EducationRequest educationRequest
    ) {
        Education education = educationRepository.findById(educationId)
                .orElseThrow(() -> new NotFoundException("Education with id " + educationId + " not found"));
        if (!education.getResume().getId().equals(resumeId)) {
            throw new UnauthorizedException(
                    "Education with id " + educationId +
                            " does not belong to resume with id " + resumeId
            );
        }
        resumeService.getResumeEntity(resumeId, candidateId);
        education.setInstitutionName(educationRequest.getInstitution());
        education.setDegree(educationRequest.getDegree());
        education.setFieldOfStudy(educationRequest.getFieldOfStudy());
        education.setGrade(educationRequest.getGrade());
        education.setStartDate(educationRequest.getStartDate());
        education.setEndDate(educationRequest.getEndDate());
        education.setIsCurrentlyAttending(educationRequest.getIsCurrentlyStudying());
        education.setDescription(educationRequest.getDescription());
        education.setDisplayOrder(
                educationRequest.getDisplayOrder() != null ?
                        educationRequest.getDisplayOrder() :
                        education.getDisplayOrder()
        );


        return EducationMapper.toEducationResponse(educationRepository.save(education));
    }

    @Override
    public void deleteEducation(Long resumeId, Long candidateId, Long educationId) {
        Education education = educationRepository.findById(educationId)
                .orElseThrow(() -> new NotFoundException("Education with id " + educationId + " not found"));
        if (!education.getResume().getId().equals(resumeId)) {
            throw new UnauthorizedException(
                    "Education with id " + educationId +
                            " does not belong to resume with id " + resumeId
            );
        }
        resumeService.getResumeEntity(resumeId, candidateId);
        educationRepository.delete(education);
    }
}
