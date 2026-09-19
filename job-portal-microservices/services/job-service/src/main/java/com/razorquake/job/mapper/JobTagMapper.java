package com.razorquake.job.mapper;

import com.razorquake.job.dto.JobTagRequest;
import com.razorquake.job.dto.JobTagResponse;
import com.razorquake.job.model.JobTag;

public class JobTagMapper {

    public static JobTagResponse mapToJobTagResponse(JobTag jobTag) {
        return JobTagResponse.builder()
                .id(jobTag.getId())
                .name(jobTag.getName())
                .slug(jobTag.getSlug())
                .build();
    }

    public static JobTag mapToJobTagEntity(JobTagRequest jobTagRequest, String slug) {
        return JobTag.builder()
                .name(jobTagRequest.getName())
                .slug(slug)
                .build();
    }
}
