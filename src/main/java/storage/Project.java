package storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDate;

import users.Applicant;
import users.HDBManager;
import users.HDBOfficer;
import users.MaritalStatus;

public class Project {
    private String projectName;
    private String neighbourhood;
    private LocalDate openingDate;
    private LocalDate closingDate;
    private HDBManager HDBManagerInCharge;
    private HashMap<String, Integer> numberOfUnits; // key: flatType, value: number of units
    private ArrayList<Applicant> applicants;
    private ArrayList<HDBOfficer> HDBOfficers;
    private ArrayList<Enquiry> enquiries;

    private int availableHDBOfficerSlots = 10; // maximum available slots for HDB officers
    private boolean projectVisibility;

    public Project(String projectName, String neighbourhood, LocalDate openingDate, LocalDate closingDate, HDBManager HDBManagerInCharge) {
        this.projectName = projectName;
        this.neighbourhood = neighbourhood;
        this.openingDate = openingDate;
        this.closingDate = closingDate;
        this.HDBManagerInCharge = HDBManagerInCharge;
        this.numberOfUnits = new HashMap<>();
        this.applicants = new ArrayList<>();
        this.HDBOfficers = new ArrayList<>();
        this.enquiries = new ArrayList<>();
    }

    public String getProjectName() { return projectName; }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getNeighbourhood() { return neighbourhood; }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public LocalDate getOpeningDate() { return openingDate; }

    public void setOpeningDate(LocalDate openingDate) {
        this.openingDate = openingDate;
    }

    public LocalDate getClosingDate() { return closingDate; }

    public void setClosingDate(LocalDate closingDate) {
        this.closingDate = closingDate;
    }

    public HDBManager getHDBManagerInCharge() { return HDBManagerInCharge; }

    public void setHDBManagerInCharge(HDBManager HDBManagerInCharge) {
        this.HDBManagerInCharge = HDBManagerInCharge;
    }

    public HashMap<String, Integer> getNumberOfUnits() { return numberOfUnits; }

    public void setNumberOfUnits(HashMap<String, Integer> numberOfUnits) {
        this.numberOfUnits = numberOfUnits;
    }

    public ArrayList<Applicant> getApplicants() { return applicants; }

    public void setApplicants(ArrayList<Applicant> applicants) {
        this.applicants = applicants;
    }

    public ArrayList<HDBOfficer> getHDBOfficers() { return HDBOfficers; }

    public void setHDBOfficers(ArrayList<HDBOfficer> HDBOfficers) {
        this.HDBOfficers = HDBOfficers;
    }

    public ArrayList<Enquiry> getEnquiries() { return enquiries; }

    public void setEnquiries(ArrayList<Enquiry> enquiries) {
        this.enquiries = enquiries;
    }

    // method to check flat availability
    public void updateFlatAvailability(String flatType, int numberOfUnits) {
        this.numberOfUnits.put(flatType, numberOfUnits);
    }

    public void toggleProjectVisibility() {
        if (projectVisibility) {
            projectVisibility = false;
        }
        else projectVisibility = true;
    }
}
