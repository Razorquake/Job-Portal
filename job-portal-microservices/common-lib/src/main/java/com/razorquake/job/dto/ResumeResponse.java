package com.razorquake.job.dto;

import com.razorquake.job.domain.ResumeTemplate;
import com.razorquake.job.domain.ResumeVisibility;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeResponse {

    private Long id;
    private Long candidateId;
    private String title;
    private ResumeTemplate template;
    private ResumeVisibility visibility;
    private Boolean isDefault;
    private PersonalInfoResponse personalInfo;
    private String summary;
    private Integer completionScore;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

  private List<WorkExperienceResponse> workExperiences;
  private List<EducationResponse> educations;
  private List<ResumeSkillResponse> skills;
  private List<ProjectResponse> projects;
//  private List<CertificationResponse> certifications;
//  private List<AwardResponse> awards;
  private List<LanguageResponse> languages;

}
