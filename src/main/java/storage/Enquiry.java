package storage;

public class Enquiry {
    private String userID; //name of asker
    private String enquiry; //the question itself
    private String answer;
    private String projectName;

    public Enquiry(String userID, String enquiry, String projectName) {
        this.userID = userID;
        this.enquiry = enquiry;
        this.projectName = projectName;
    }
}
