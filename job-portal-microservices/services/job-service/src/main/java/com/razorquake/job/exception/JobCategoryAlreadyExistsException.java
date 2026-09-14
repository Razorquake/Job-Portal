package com.razorquake.job.exception;

public class JobCategoryAlreadyExistsException extends RuntimeException {
    public JobCategoryAlreadyExistsException(String jobCategoryAlreadyExists) {
        super(jobCategoryAlreadyExists);
    }
}
