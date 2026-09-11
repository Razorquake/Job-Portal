package com.razorquake.job.service;


import com.razorquake.job.domain.CompanyStatus;
import com.razorquake.job.domain.CompanyType;
import com.razorquake.job.domain.IndustryType;
import com.razorquake.job.dto.CompanyRequest;
import com.razorquake.job.dto.CompanyResponse;

import java.util.List;

public interface CompanyService {

    CompanyResponse createCompany(Long ownerId, CompanyRequest companyRequest);
    CompanyResponse getCompanyById(Long id);
    CompanyResponse getMyCompany(Long ownerId);
    List<CompanyResponse> getAllCompanies(
            CompanyType companyType,
            IndustryType industryType,
            CompanyStatus companyStatus
    );
    CompanyResponse updateCompany(Long companyId, Long ownerId, CompanyRequest companyRequest);
    CompanyResponse verifyCompany(Long companyId);
    void deleteCompany(Long companyId, Long ownerId);
    CompanyResponse deactivateCompany(Long companyId);


}
