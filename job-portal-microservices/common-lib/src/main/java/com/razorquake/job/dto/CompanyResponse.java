package com.razorquake.job.dto;

import com.razorquake.job.domain.CompanySize;
import com.razorquake.job.domain.CompanyStatus;
import com.razorquake.job.domain.CompanyType;
import com.razorquake.job.domain.IndustryType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyResponse {

    private Long id;
    private String name;
    private String slug;
    private String tagline;
    private String description;
    private String logoUrl;
    private String website;
    private String coverImageUrl;
    private String email;
    private String phone;
    private Integer foundedYear;
    private boolean isVerified;

    private CompanySize companySize;
    private CompanyType companyType;
    private IndustryType industryType;
    private CompanyStatus companyStatus;

    private String registrationNumber;

    private Long ownerId;

    private List<SocialLinkResponse> socialLinks;

    private Boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
