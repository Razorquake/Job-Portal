package com.razorquake.job.service;

import com.razorquake.job.dto.LanguageRequest;
import com.razorquake.job.dto.LanguageResponse;

import java.util.List;

public interface LanguageService {

    LanguageResponse createLanguage(
            Long resumeId,
            Long candidateId,
            LanguageRequest languageRequest
    );

    List<LanguageResponse> getLanguagesByResumeId(Long resumeId);

    LanguageResponse updateLanguage(
            Long resumeId,
            Long candidateId,
            Long languageId,
            LanguageRequest languageRequest
    );

    void deleteLanguage(
            Long resumeId,
            Long candidateId,
            Long languageId
    );
}
