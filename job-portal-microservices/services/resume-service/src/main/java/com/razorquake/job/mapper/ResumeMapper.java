package com.razorquake.job.mapper;

import com.razorquake.job.dto.*;
import com.razorquake.job.model.PersonalInfo;
import com.razorquake.job.model.Resume;

import java.util.List;

public class ResumeMapper {

    public static PersonalInfoResponse toPersonalInfoResponse(PersonalInfo personalInfo) {

        if (personalInfo == null)
            return null;

        return PersonalInfoResponse.builder()
                .firstName(personalInfo.getFirstName())
                .lastName(personalInfo.getLastName())
                .email(personalInfo.getEmail())
                .phone(personalInfo.getPhone())
                .headline(personalInfo.getHeadline())
                .country(personalInfo.getCountry())
                .city(personalInfo.getCity())
                .githubUrl(personalInfo.getGithubUrl())
                .linkedinUrl(personalInfo.getLinkedinUrl())
                .portfolioUrl(personalInfo.getPortfolioUrl())
                .websiteUrl(personalInfo.getWebsiteUrl())
                .build();
    }

    public static Resume toEntity(Long candidateId, CreateResumeRequest request) {
        return Resume.builder()
                .candidateId(candidateId)
                .title(request.getTitle())
                .template(request.getTemplate())
                .visibility(request.getVisibility())
                .isDefault(Boolean.TRUE.equals(request.getIsDefault()))
                .build();
    }

    public static ResumeResponse toResponse(
            Resume resume,
            List<WorkExperienceResponse> workExperiences,
            List<EducationResponse> educations,
            List<ResumeSkillResponse> skills,
            List<ProjectResponse> projects,
            List<LanguageResponse> languages
    ) {
        return ResumeResponse.builder()
                .id(resume.getId())
                .title(resume.getTitle())
                .template(resume.getTemplate())
                .visibility(resume.getVisibility())
                .isDefault(resume.getIsDefault())
                .personalInfo(toPersonalInfoResponse(resume.getPersonalInfo()))
                .summary(resume.getSummary())
                .completionScore(resume.getCompletionScore())
                .workExperiences(workExperiences)
                .educations(educations)
                .skills(skills)
                .projects(projects)
                .languages(languages)
                .createdAt(resume.getCreatedAt())
                .updatedAt(resume.getUpdatedAt())
                .build();
    }

}
