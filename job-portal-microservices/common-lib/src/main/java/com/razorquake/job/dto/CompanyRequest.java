package com.razorquake.job.dto;

import com.razorquake.job.domain.CompanySize;
import com.razorquake.job.domain.CompanyType;
import com.razorquake.job.domain.IndustryType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyRequest {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    private String tagline;
    private String description;
    private String logoUrl;
    @Pattern(regexp = "^(https?://).*", message = "Website must be a valid URL")
    private String website;
    private String coverImageUrl;
    @Email(message = "Email must be a valid email address")
    private String email;

    private String phone;

    @Min(value = 1800, message = "Founded year must be greater than or equal to 1800")
    @Max(value = 2100, message = "Founded year must be less than or equal to current year")
    private Integer foundedYear;

    @NotNull(message = "Company size cannot be null")
    private CompanySize companySize;

    @NotNull(message = "Company type cannot be null")
    private CompanyType companyType;

    @NotNull(message = "Industry type cannot be null")
    private IndustryType industryType;

    private String registrationNumber;

    private List<SocialLinkResponse> socialLinks;
}
