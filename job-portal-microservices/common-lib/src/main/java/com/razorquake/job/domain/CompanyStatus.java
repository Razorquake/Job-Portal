package com.razorquake.job.domain;

public enum CompanyStatus {
    ACTIVE, // verified and operational
    SUSPENDED, // temporarily blocked by admin
    REJECTED,
    PENDING_VERIFICATION // just registered, awaiting admin review
}
