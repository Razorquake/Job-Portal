package com.razorquake.job.service.impl;

import com.razorquake.job.dto.ResumeSkillRequest;
import com.razorquake.job.dto.ResumeSkillResponse;
import com.razorquake.job.exception.NotFoundException;
import com.razorquake.job.mapper.ResumeSkillMapper;
import com.razorquake.job.model.Resume;
import com.razorquake.job.model.ResumeSkill;
import com.razorquake.job.repository.ResumeSkillRepository;
import com.razorquake.job.service.ResumeService;
import com.razorquake.job.service.ResumeSkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeSkillServiceImpl implements ResumeSkillService {

    private final ResumeSkillRepository resumeSkillRepository;
    private final ResumeService resumeService;

    @Override
    public ResumeSkillResponse addResumeSkill(
            Long resumeId,
            Long candidateId,
            ResumeSkillRequest resumeSkillRequest
    ) {
        Resume resume = resumeService.getResumeEntity(resumeId, candidateId);
        return ResumeSkillMapper.toResponse(
                resumeSkillRepository.save(
                        ResumeSkillMapper.toModel(resumeSkillRequest, resume)
                )
        );
    }

    @Override
    public List<ResumeSkillResponse> getResumeSkillsByResumeId(Long resumeId) {
        return resumeSkillRepository.findByResume_IdOrderByDisplayOrderAsc(resumeId)
                .stream()
                .map(ResumeSkillMapper::toResponse)
                .toList();
    }

    @Override
    public ResumeSkillResponse updateResumeSkill(Long resumeId, Long candidateId, Long resumeSkillId, ResumeSkillRequest resumeSkillRequest) {

        ResumeSkill resumeSkill = resumeSkillRepository.findById(resumeSkillId)
                .orElseThrow(() -> new NotFoundException("ResumeSkill with id " + resumeSkillId + " not found"));
        resumeService.getResumeEntity(resumeId, candidateId);

        resumeSkill.setDisplayOrder(
                resumeSkillRequest.getDisplayOrder() != null ?
                        resumeSkillRequest.getDisplayOrder() :
                        resumeSkill.getDisplayOrder()
        );
        resumeSkill.setProficiencyLevel(resumeSkillRequest.getProficiencyLevel());
        resumeSkill.setYearsOfExperience(resumeSkillRequest.getYearsOfExperience());
        resumeSkill.setSkill(resumeSkillRequest.getSkillName());

        return ResumeSkillMapper.toResponse(resumeSkillRepository.save(resumeSkill));
    }

    @Override
    public void deleteResumeSkill(Long resumeId, Long candidateId, Long resumeSkillId) {
        ResumeSkill resumeSkill = resumeSkillRepository.findById(resumeSkillId)
                .orElseThrow(() -> new NotFoundException("ResumeSkill with id " + resumeSkillId + " not found"));
        resumeService.getResumeEntity(resumeId, candidateId);
        resumeSkillRepository.delete(resumeSkill);
    }
}
