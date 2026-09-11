package com.razorquake.job.repository.specification;

import com.razorquake.job.domain.CompanyStatus;
import com.razorquake.job.domain.CompanyType;
import com.razorquake.job.domain.IndustryType;
import com.razorquake.job.model.Company;
import org.springframework.data.jpa.domain.Specification;

public class CompanySpecification {

    private static Specification<Company> byCompanyType(CompanyType companyType) {
        return (root, _, criteriaBuilder) -> criteriaBuilder.equal(root.get("companyType"), companyType);
    }

    private static Specification<Company> byIndustryType(IndustryType industryType) {
        return (root, _, criteriaBuilder) -> criteriaBuilder.equal(root.get("industryType"), industryType);
    }

    private static Specification<Company> byStatus(CompanyStatus companyStatus) {
        return (root, _, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), companyStatus);
    }

    private static Specification<Company> matchAll() {
        return (_, _, criteriaBuilder) -> criteriaBuilder.conjunction();
    }

    private static Specification<Company> combine(Specification<Company> spec, Specification<Company> other) {
        if (spec == null) {
            return other;
        }
        if (other == null) {
            return spec;
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(spec.toPredicate(root, query, criteriaBuilder), other.toPredicate(root, query, criteriaBuilder));
    }

    public static Specification<Company> buildSpecification(
            CompanyStatus companyStatus,
            CompanyType companyType,
            IndustryType industryType
    ) {
        Specification<Company> spec = matchAll();
        if (companyStatus != null) {
            spec = combine(spec, byStatus(companyStatus));
        }
        if (companyType != null) {
            spec = combine(spec, byCompanyType(companyType));
        }
        if (industryType != null) {
            spec = combine(spec, byIndustryType(industryType));
        }
        return spec;
    }
}
