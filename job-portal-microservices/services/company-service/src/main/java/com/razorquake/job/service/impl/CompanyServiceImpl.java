package com.razorquake.job.service.impl;

import com.razorquake.job.domain.CompanyStatus;
import com.razorquake.job.domain.CompanyType;
import com.razorquake.job.domain.IndustryType;
import com.razorquake.job.dto.CompanyRequest;
import com.razorquake.job.dto.CompanyResponse;
import com.razorquake.job.exception.CompanyAlreadyExistsException;
import com.razorquake.job.exception.CompanyNotFoundException;
import com.razorquake.job.exception.UnauthorizedException;
import com.razorquake.job.mapper.CompanyMapper;
import com.razorquake.job.model.Company;
import com.razorquake.job.repository.CompanyRepository;
import com.razorquake.job.repository.specification.CompanySpecification;
import com.razorquake.job.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public CompanyResponse createCompany(Long ownerId, CompanyRequest companyRequest) {
        if (companyRepository.existsByOwnerId(ownerId))
            throw new CompanyAlreadyExistsException(
                    "Owner with id " + ownerId + " already has a company. Only one company per owner is allowed."
            );
        if (companyRepository.existsByName(companyRequest.getName()))
            throw new CompanyAlreadyExistsException(
                    "Company with name " + companyRequest.getName() + " already exists. Only one company per name is allowed."
            );
        if (companyRequest.getRegistrationNumber() != null && companyRepository.existsByRegistrationNumber(companyRequest.getRegistrationNumber()))
            throw new CompanyAlreadyExistsException(
                    "Company with registration number " + companyRequest.getRegistrationNumber() + " already exists. Only one company per registration number is allowed."
            );
        String slug = generateUniqueSlug(companyRequest.getName());
        Company company = Company.builder()
                .name(companyRequest.getName())
                .slug(slug)
                .tagline(companyRequest.getTagline())
                .description(companyRequest.getDescription())
                .logoUrl(companyRequest.getLogoUrl())
                .coverImageUrl(companyRequest.getCoverImageUrl())
                .website(companyRequest.getWebsite())
                .email(companyRequest.getEmail())
                .phone(companyRequest.getPhone())
                .foundedYear(companyRequest.getFoundedYear())
                .companySize(companyRequest.getCompanySize())
                .companyType(companyRequest.getCompanyType())
                .industryType(companyRequest.getIndustryType())
                .registrationNumber(companyRequest.getRegistrationNumber())
                .status(CompanyStatus.PENDING_VERIFICATION)
                .isActive(true)
                .ownerId(ownerId)
                .socialLinks(companyRequest.getSocialLinks()
                        .stream()
                        .map(CompanyMapper::toSocialLink)
                        .toList())
                .coverImageUrl(companyRequest.getCoverImageUrl())
                .build();

        return CompanyMapper.toCompanyResponse(companyRepository.save(company));
    }

    private String generateUniqueSlug(String name) {
        String base = name.toLowerCase().replaceAll("[^a-z0-9\\s-]", "")
                .trim().replaceAll("[\\s-]", "-");

        String slug = base;
        int counter = 1;
        while (companyRepository.existsBySlug(slug)) {
            slug = base + "-" + counter++;
        }
        return slug;
    }

    private Company getCompanyEntityById(Long id) {
        return companyRepository.findById(id)
                        .orElseThrow(
                                () -> new CompanyNotFoundException("Company not found")
                        );
    }

    @Override
    public CompanyResponse getCompanyById(Long id) {
        return CompanyMapper.toCompanyResponse(getCompanyEntityById(id));
    }

    @Override
    public CompanyResponse getMyCompany(Long ownerId) {
        return CompanyMapper.toCompanyResponse(companyRepository.findByOwnerId(ownerId).orElseThrow(
                () -> new CompanyNotFoundException("Company not found")
        ));
    }

    @Override
    public List<CompanyResponse> getAllCompanies(
            CompanyType companyType,
            IndustryType industryType,
            CompanyStatus companyStatus
    ) {
        Specification<Company> spec = CompanySpecification.buildSpecification(companyStatus, companyType, industryType);
        return companyRepository.findAll(spec).stream().map(CompanyMapper::toCompanyResponse).toList();
    }

    @Override
    public CompanyResponse updateCompany(Long companyId, Long ownerId, CompanyRequest companyRequest) {
        Company company = getCompanyEntityById(companyId);
        if (!company.getName().equals(companyRequest.getName()) &&
        companyRepository.existsByName(companyRequest.getName()))
            throw new CompanyAlreadyExistsException("Company already exists with name: " + companyRequest.getName());
        if (companyRequest.getName() != null
                && !company.getName().equals(companyRequest.getName())
                && companyRepository.existsByRegistrationNumber(companyRequest.getRegistrationNumber())
        )
            throw new CompanyAlreadyExistsException("Company already exists with registration number: " + companyRequest.getRegistrationNumber());
        company.setName(companyRequest.getName());
        company.setTagline(companyRequest.getTagline());
        company.setDescription(companyRequest.getDescription());
        company.setLogoUrl(companyRequest.getLogoUrl());
        company.setCoverImageUrl(companyRequest.getCoverImageUrl());
        company.setWebsite(companyRequest.getWebsite());
        company.setEmail(companyRequest.getEmail());
        company.setPhone(companyRequest.getPhone());
        company.setFoundedYear(companyRequest.getFoundedYear());
        company.setCompanySize(companyRequest.getCompanySize());
        company.setCompanyType(companyRequest.getCompanyType());
        company.setIndustryType(companyRequest.getIndustryType());
        company.setRegistrationNumber(companyRequest.getRegistrationNumber());
        company.setSocialLinks(
                companyRequest.getSocialLinks()
                        .stream().map(CompanyMapper::toSocialLink).toList()
        );

        return CompanyMapper.toCompanyResponse(companyRepository.save(company));
    }

    @Override
    public CompanyResponse verifyCompany(Long companyId) {
        Company company = getCompanyEntityById(companyId);
        company.setStatus(CompanyStatus.ACTIVE);
        company.setVerified(true);
        return CompanyMapper.toCompanyResponse(companyRepository.save(company));
    }

    @Override
    public void deleteCompany(Long companyId, Long ownerId) {
        Company company = getCompanyEntityById(companyId);
        if (!company.getOwnerId().equals(ownerId))
            throw new UnauthorizedException("You are not authorized to delete this company");
        companyRepository.deleteById(companyId);
    }

    @Override
    public CompanyResponse deactivateCompany(Long companyId) {
        Company company = getCompanyEntityById(companyId);
        company.setIsActive(false);
        company.setStatus(CompanyStatus.SUSPENDED);
        return CompanyMapper.toCompanyResponse(companyRepository.save(company));
    }
}
