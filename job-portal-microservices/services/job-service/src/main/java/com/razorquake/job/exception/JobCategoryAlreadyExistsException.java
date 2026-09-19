package com.razorquake.job.exception;

public class JobCategoryAlreadyExistsException extends AlreadyExistsException {
    public JobCategoryAlreadyExistsException(String jobCategoryAlreadyExists) {
        super(jobCategoryAlreadyExists);
    }
}
