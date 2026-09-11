package com.razorquake.job.mapper;

import com.razorquake.job.dto.CompanyResponse;
import com.razorquake.job.dto.SocialLinkResponse;
import com.razorquake.job.model.Company;
import com.razorquake.job.model.SocialLink;

import java.util.List;

public class CompanyMapper {

    public static SocialLinkResponse toSocialLinkResponse(SocialLink socialLink) {
        return SocialLinkResponse.builder()
                .platform(socialLink.getPlatform())
                .url(socialLink.getUrl())
                .build();
    }

    public static SocialLink toSocialLink(SocialLinkResponse socialLinkResponse) {
        return SocialLink.builder()
                .platform(socialLinkResponse.getPlatform())
                .url(socialLinkResponse.getUrl())
                .build();
    }

    public static CompanyResponse toCompanyResponse(Company company) {
        List<SocialLinkResponse> socialLinks = company
                .getSocialLinks()
                .stream()
                .map(CompanyMapper::toSocialLinkResponse)
                .toList();
        return CompanyResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .slug(company.getSlug())
                .tagline(company.getTagline())
                .description(company.getDescription())
                .logoUrl(company.getLogoUrl())
                .coverImageUrl(company.getCoverImageUrl())
                .website(company.getWebsite())
                .email(company.getEmail())
                .phone(company.getPhone())
                .foundedYear(company.getFoundedYear())
                .companySize(company.getCompanySize())
                .companyType(company.getCompanyType())
                .industryType(company.getIndustryType())
                .companyStatus(company.getStatus())
                .isActive(company.getIsActive())
                .registrationNumber(company.getRegistrationNumber())
                .ownerId(company.getOwnerId())
                .socialLinks(socialLinks)
                .createdAt(company.getCreatedAt())
                .updatedAt(company.getUpdatedAt())
                .isVerified(company.isVerified())
                .build();
    }
}
