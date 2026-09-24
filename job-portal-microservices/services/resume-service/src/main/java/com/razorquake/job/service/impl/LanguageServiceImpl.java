package com.razorquake.job.service.impl;

import com.razorquake.job.dto.LanguageRequest;
import com.razorquake.job.dto.LanguageResponse;
import com.razorquake.job.exception.NotFoundException;
import com.razorquake.job.exception.UnauthorizedException;
import com.razorquake.job.mapper.LanguageMapper;
import com.razorquake.job.model.Language;
import com.razorquake.job.model.Resume;
import com.razorquake.job.repository.LanguageRepository;
import com.razorquake.job.service.LanguageService;
import com.razorquake.job.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;
    private final ResumeService resumeService;

    @Override
    public LanguageResponse createLanguage(
            Long resumeId,
            Long candidateId,
            LanguageRequest languageRequest
    ) {
        Resume resume = resumeService.getResumeEntity(resumeId, candidateId);

        return LanguageMapper.toResponse(
                languageRepository.save(
                        LanguageMapper.toLanguage(languageRequest, resume)
                )
        );
    }

    @Override
    public List<LanguageResponse> getLanguagesByResumeId(Long resumeId) {
        return languageRepository
                .findByResume_IdOrderByDisplayOrderAsc(resumeId)
                .stream()
                .map(LanguageMapper::toResponse)
                .toList();
    }

    @Override
    public LanguageResponse updateLanguage(
            Long resumeId,
            Long candidateId,
            Long languageId,
            LanguageRequest languageRequest
    ) {
        Language language = languageRepository.findById(languageId)
                .orElseThrow(() -> new NotFoundException("Language with id " + languageId + " not found"));
        if (!language.getResume().getId().equals(resumeId)) {
            throw new UnauthorizedException(
                    "Language with id " + languageId +
                            " does not belong to resume with id " + resumeId
            );
        }
        resumeService.getResumeEntity(resumeId, candidateId);
        language.setName(languageRequest.getLanguageName());
        language.setProficiency(languageRequest.getProficiency());
        language.setDisplayOrder(
                languageRequest.getDisplayOrder() != null ?
                        languageRequest.getDisplayOrder() :
                        language.getDisplayOrder()
        );
        return LanguageMapper.toResponse(languageRepository.save(language));
    }

    @Override
    public void deleteLanguage(
            Long resumeId,
            Long candidateId,
            Long languageId
    ) {
        Language language = languageRepository.findById(languageId)
                .orElseThrow(() -> new NotFoundException("Language with id " + languageId + " not found"));
        if (!language.getResume().getId().equals(resumeId)) {
            throw new UnauthorizedException(
                    "Language with id " + languageId +
                            " does not belong to resume with id " + resumeId
            );
        }
        resumeService.getResumeEntity(resumeId, candidateId);
        languageRepository.delete(language);
    }
}
