package com.razorquake.job.service.impl;

import com.razorquake.job.dto.CreateResumeRequest;
import com.razorquake.job.dto.PersonalInfoResponse;
import com.razorquake.job.dto.ResumeResponse;
import com.razorquake.job.exception.UnauthorizedException;
import com.razorquake.job.mapper.ResumeMapper;
import com.razorquake.job.model.PersonalInfo;
import com.razorquake.job.model.Resume;
import com.razorquake.job.repository.ResumeRepository;
import com.razorquake.job.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;

    @Override
    public ResumeResponse createResume(Long candidateId, CreateResumeRequest request) {
        if (Boolean.TRUE.equals(request.getIsDefault())) {
            resumeRepository.findByCandidateIdAndIsDefaultTrue(candidateId)
                    .ifPresent(existing -> {
                        existing.setIsDefault(false);
                        resumeRepository.save(existing);
                    });
        }
        return ResumeMapper.toResponse(
                resumeRepository.save(
                        ResumeMapper.toEntity(candidateId, request)
                )
        );
    }

    @Override
    public ResumeResponse getResume(Long candidateId, Long resumeId) {
        Resume resume = resumeRepository
                .findByIdAndCandidateId(resumeId, candidateId)
                .orElseThrow(() -> new UnauthorizedException("Resume access denied"));
        return ResumeMapper.toResponse(resume);
    }

    @Override
    public List<ResumeResponse> getResumes(Long candidateId) {
        return resumeRepository.findByCandidateIdAndIsActiveTrue(candidateId)
                .stream().map(ResumeMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteResume(Long candidateId, Long resumeId) {
        Resume resume = resumeRepository.findByIdAndCandidateId(resumeId, candidateId)
                .orElseThrow(() -> new UnauthorizedException("Resume access denied"));
        resume.setIsActive(false);
        resume.setIsDefault(false);
        resumeRepository.save(resume);
    }

    @Override
    public ResumeResponse updatePersonalInfo(Long candidateId, Long resumeId, PersonalInfoResponse req) {
        Resume resume = resumeRepository.findByIdAndCandidateId(resumeId, candidateId)
                .orElseThrow(() -> new UnauthorizedException("Resume access denied"));
        PersonalInfo info = resume.getPersonalInfo();
        if (info == null)
            info = new PersonalInfo();
        if (req.getFirstName() != null)
            info.setFirstName(req.getFirstName());
        if (req.getLastName() != null)
            info.setLastName(req.getLastName());
        if (req.getHeadline() != null)
            info.setHeadline(req.getHeadline());
        if (req.getPhone() != null)
            info.setPhone(req.getPhone());
        if (req.getEmail() != null)
            info.setEmail(req.getEmail());
        if (req.getCity() != null)
            info.setCity(req.getCity());
        if (req.getCountry() != null)
            info.setCountry(req.getCountry());
        if (req.getLinkedinUrl() != null)
            info.setLinkedinUrl(req.getLinkedinUrl());
        if (req.getGithubUrl() != null)
            info.setGithubUrl(req.getGithubUrl());
        if (req.getPortfolioUrl() != null)
            info.setPortfolioUrl(req.getPortfolioUrl());
        if (req.getWebsiteUrl() != null)
            info.setWebsiteUrl(req.getWebsiteUrl());

        resume.setPersonalInfo(info);

        return ResumeMapper.toResponse(
                resumeRepository.save(resume)
        );
    }

    @Override
    public ResumeResponse updateSummary(Long candidateId, Long resumeId, String summary) {
        Resume resume = resumeRepository.findByIdAndCandidateId(resumeId, candidateId)
                .orElseThrow(() -> new UnauthorizedException("Resume access denied"));
        resume.setSummary(summary);
        return ResumeMapper.toResponse(resumeRepository.save(resume));
    }

    @Override
    public ResumeResponse setDefaultResume(Long candidateId, Long resumeId) {
        Resume resume = resumeRepository.findByIdAndCandidateId(resumeId, candidateId)
                .orElseThrow(() -> new UnauthorizedException("Resume access denied"));
        resumeRepository.findByCandidateIdAndIsDefaultTrue(candidateId)
                .ifPresent(existing -> {
                    existing.setIsDefault(false);
                    resumeRepository.save(existing);
                });
        resume.setIsDefault(true);
        return ResumeMapper.toResponse(resumeRepository.save(resume));
    }

    @Override
    public Resume getResumeEntity(
            Long resumeId,
            Long candidateId
            ) {
        return resumeRepository
                .findByIdAndCandidateId(resumeId, candidateId)
                .orElseThrow(
                        () -> new UnauthorizedException(
                                "Candidate with id: " +
                                        candidateId +
                                        " does not have access to resume with id: " +
                                        resumeId
                        )
                );
    }
}
