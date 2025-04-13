package storage;

public class BTOApplication {
    private final String applicantID;
    private final String projectName;
    private final double price;
    private final String officerInCharge;
    private final FlatType flatType;
    private ApplicationStatus applicationStatus;

    public BTOApplication(String applicantID, String projectName, String price, String officerInCharge, String flatType,String applicationStatus) {
        this.applicantID = applicantID;
        this.projectName = projectName;
        this.price = Double.parseDouble(price);
        this.officerInCharge = officerInCharge;
        this.flatType = FlatType.valueOf(flatType);
        this.applicationStatus = ApplicationStatus.valueOf(applicationStatus);
    }

    public String getApplicantID() { return applicantID; }
    public String getProjectName() { return projectName; }
    public double getPrice() { return price; }
    public String getOfficerInCharge() { return officerInCharge; }
    public FlatType getFlatType() { return flatType; }
    public ApplicationStatus getApplicationStatus() { return applicationStatus; }

    public void setApplicationStatus(ApplicationStatus status) {
        this.applicationStatus = status;
    }
    public String toString() {
        return "BTOApplication{" +
                "applicantID='" + applicantID + '\'' +
                ", projectName='" + projectName + '\'' +
                ", price='" + price + '\'' +
                ", officerInCharge='" + officerInCharge + '\'' +
                ", flatType=" + flatType +
                ", applicationStatus=" + applicationStatus +
                '}';
    }
}
