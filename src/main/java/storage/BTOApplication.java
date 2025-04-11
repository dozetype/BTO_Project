package storage;

import users.RegistrationStatus;

public class BTOApplication {
    private final String applicantID;
    private final String projectName;
    private final String price;
    private final String officerInCharge;
    private final FlatType flatType;
    private RegistrationStatus applicationStatus;

    public BTOApplication(String applicantID, String projectName, String price, String officerInCharge, String flatType,String applicationStatus) {
        this.applicantID = applicantID;
        this.projectName = projectName;
        this.price = price;
        this.officerInCharge = officerInCharge;
        this.flatType = FlatType.valueOf(flatType);
        this.applicationStatus = RegistrationStatus.valueOf(applicationStatus);
    }

    public String getApplicantID() { return applicantID; }
    public String getProjectName() { return projectName; }
    public String getPrice() { return price; }
    public String getOfficerInCharge() { return officerInCharge; }
    public FlatType getFlatType() { return flatType; }
    public RegistrationStatus getApplicationStatus() { return applicationStatus; }
}
