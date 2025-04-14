package storage;

public enum ApplicationStatus {
    PENDING, /* Entry status upon application – No conclusive decision made about the outcome of the application */
    SUCCESSFUL, /* Outcome of the application is successful, hence invited to make a flat booking with the HDB Officer */
    UNSUCCESSFUL, /* Outcome of the application is unsuccessful, hence cannot make a flat booking for this application. Applicant may apply for another project. */
    BOOKED, /* Secured a unit after a successful application and completed a flat booking with the HDB Officer. */
    WITHDRAWING,
    WITHDRAWN,
    WITHDRAWAL_REJECTED
}
