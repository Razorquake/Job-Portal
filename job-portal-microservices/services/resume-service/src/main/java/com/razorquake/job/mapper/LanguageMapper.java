package com.razorquake.job.mapper;

import com.razorquake.job.dto.LanguageRequest;
import com.razorquake.job.dto.LanguageResponse;
import com.razorquake.job.model.Language;
import com.razorquake.job.model.Resume;

public class LanguageMapper {

    public static Language toLanguage(LanguageRequest request, Resume resume) {
        return Language.builder()
                .resume(resume)
                .name(request.getLanguageName())
                .proficiency(request.getProficiency())
                .displayOrder(request.getDisplayOrder() != null ? request.getDisplayOrder() : 0)
                .build();
    }

    public static LanguageResponse toResponse(Language language) {
        return LanguageResponse.builder()
                .id(language.getId())
                .name(language.getName())
                .proficiency(language.getProficiency())
                .displayOrder(language.getDisplayOrder())
                .build();
    }
}
