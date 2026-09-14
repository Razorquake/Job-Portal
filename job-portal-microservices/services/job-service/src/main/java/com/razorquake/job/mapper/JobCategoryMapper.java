package com.razorquake.job.mapper;

import com.razorquake.job.dto.JobCategoryRequest;
import com.razorquake.job.dto.JobCategoryResponse;
import com.razorquake.job.model.JobCategory;

import java.util.List;

public class JobCategoryMapper {
    public static JobCategory toJobCategory(JobCategoryRequest jobCategoryRequest, JobCategory parent, String slug) {
        return JobCategory.builder()
                .name(jobCategoryRequest.getName())
                .slug(slug)
                .description(jobCategoryRequest.getDescription())
                .iconUrl(jobCategoryRequest.getIconUrl())
                .parent(parent)
                .build();
    }

    public static JobCategoryResponse toJobCategoryResponse(JobCategory jobCategory, boolean includeChildren) {

        List<JobCategoryResponse> children = includeChildren ? jobCategory.getSubCategories()
                .stream()
                .map(subcategory -> toJobCategoryResponse(subcategory, false))
                .toList() : null;

        return JobCategoryResponse.builder()
                .id(jobCategory.getId())
                .name(jobCategory.getName())
                .slug(jobCategory.getSlug())
                .description(jobCategory.getDescription())
                .iconUrl(jobCategory.getIconUrl())
                .active(jobCategory.getActive())
                .parentId(jobCategory.getParent() != null ? jobCategory.getParent().getId() : null)
                .parentName(jobCategory.getParent() != null ? jobCategory.getParent().getName() : null)
                .children(children)
                .build();
    }
}