package com.razorquake.job.exception;

public class JobCategoryNotFoundException extends RuntimeException {
    public JobCategoryNotFoundException(String message) {
        super(message);
    }
}
