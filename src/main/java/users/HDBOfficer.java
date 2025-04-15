package users;

import storage.Enquiry;
import storage.BTOApplication;
import storage.*;
import ui.Messages;

import java.text.SimpleDateFormat;
import java.util.*;

public class HDBOfficer extends Applicant {
    private OfficerStatus officerStatus;
    private RegistrationStatus registrationStatus;
    private String ProjectAllocated; //find out Project allocated and Registration from ProjectTeam

    public HDBOfficer(List<String> userData) {
        super(userData);
        setUserType("Officer"); //Override "Applicant"
    }


    /**
     * @param storage DataBase
     */
    public void registerToJoinProject(Storage storage) {
        for (Project p : storage.getProject().values()) {
            if (p.getProjectTeam().getOfficers().contains(getUserID())) {
                System.out.println("Cannot apply. You are an Officer in " + p.getProjectName());
                return;
            }
        }
        List<String> projectNames = new ArrayList<>();
        int count = 1;
        for (Project p : storage.getProject().values()) {
            projectNames.add(p.getProjectName());
            System.out.println(count++ + ") " + p.getProjectName());
        }
        try {
            System.out.print("Pick which Project you would like to register as Officer: ");
            String projectName = projectNames.get(ui.inputInt() - 1);
            for(BTOApplication application : storage.getBTOApplications().values()) {
                if (application.getApplicantID().equals(getUserID()) && application.getProjectName().equals(projectName)) {
                    System.out.println("You have already applied for this project as Applicant.");
                    return;
                }
            }
            storage.registerProject(getUserID(), projectName);
            System.out.println("Please wait for the result. You are applying as Officer in "+projectName);
            setRegistrationStatus(RegistrationStatus.PENDING);
            System.out.println(storage.getProject());
        } catch (Exception e) {
            System.out.println(e.getMessage() + " " + e.getCause());
        }
    }

    /**
     * @param storage DataBase
     */
    @Override
    public void viewEnquiries(Storage storage) {
        for(Enquiry e : storage.getEnquiries().values()) {
            if(e.getProjectName().equals(ProjectAllocated)) {
                System.out.println(e);
            }
        }
    }

    /**
     * @param storage DataBase
     */
    public void replyToEnquiry(Storage storage) {
        //TODO only be able to view enq with unanswered qns
        viewEnquiries(storage);
        System.out.println("Please choose the Enquiries ID: ");
        String enquiryID = ui.inputString();
        for(Enquiry e : storage.getEnquiries().values()) {
            if (e.getID().equals(enquiryID)&&e.getReply().equals("NULL")) {
                System.out.println("Please write your reply: ");
                String reply = ui.inputString();
                e.setReply(reply);
                System.out.println("Thank you for your reply!");
            }
        }
    }

    public void viewApplication(Storage storage) {
        for(BTOApplication app : storage.getBTOApplications().values()) {
            if (app.getProjectName().equals(ProjectAllocated)) {
                System.out.println(app);
            }
        }
    }
    public String generateReceipt(Storage storage) {
        viewApplication(storage);
        System.out.print("Please choose the Applicant ID: ");
        String ApplicantID = ui.inputString();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String currentDate = dateFormat.format(new Date());

        StringBuilder receipt = null;
        for (BTOApplication app : storage.getBTOApplications().values()) {
            if (app.getApplicantID().equals(ApplicantID)) {
                receipt = new StringBuilder();
                List<String> applicant = storage.getUserData(ApplicantID);

                receipt.append("=== BTO APPLICATION RECEIPT ===\n");
                receipt.append("Date: ").append(currentDate).append("\n\n");
                receipt.append("APPLICANT DETAILS:\n");
                receipt.append("Name: ").append(applicant.get(0)).append("\n");
                receipt.append("NRIC: ").append(applicant.get(1)).append("\n");
                receipt.append("Age: ").append(applicant.get(2)).append("\n");
                receipt.append("Marital Status: ").append(applicant.get(3)).append("\n\n");

                receipt.append("APPLICATION DETAILS:\n");
                receipt.append("Project Name: ").append(app.getProjectName()).append("\n");
                receipt.append("Flat Type: ").append(app.getFlatType()).append("\n");
                receipt.append("Price: ").append(app.getPrice()).append("\n");
                receipt.append("Officer In Charge: ").append(app.getOfficerInCharge()).append("\n");
                receipt.append("Status: ").append(app.getApplicationStatus()).append("\n\n");

                receipt.append("=== THANK YOU FOR YOUR APPLICATION ===\n");
            }

        }
        return receipt.toString();
    }

    public void checkProjectAllocated(Storage storage) {
        for (Project p : storage.getProject().values()) {
            if(p.getProjectTeam().getOfficers().contains(getUserID())) {
                setProjectAllocated(p.getProjectName());
                setRegistrationStatus(RegistrationStatus.SUCCESSFUL);
                setOfficerStatus(OfficerStatus.OFFICER);
                return;
            }
            else if(p.getProjectTeam().getOfficersApplying().contains(getUserID())) {
                setRegistrationStatus(RegistrationStatus.PENDING);
                setOfficerStatus(OfficerStatus.NEITHER);
                return;
            }
        }
        setRegistrationStatus(RegistrationStatus.NOT_REGISTERED);
        setOfficerStatus(OfficerStatus.NEITHER);
    }

    public void updateNumOfFlats(Storage storage){
        for (Project p : storage.getProject().values()) {
            if (p.getProjectName().equals(ProjectAllocated)) {
                System.out.println(p); // too long, need to change
                System.out.println(p.getUnits());
                int count=1;
                List<String> flatTypesList = new ArrayList<>();
                for (Map.Entry<FlatType, Integer> flatTypes : p.getUnits().entrySet()){
                    System.out.printf("%d) %-10s (Current value: %d)%n",
                            count++,
                            flatTypes.getKey(),
                            flatTypes.getValue());
                    flatTypesList.add(flatTypes.getKey().toString());
                }

                System.out.print("Please choose the Flat Type: ");
                int flatType = ui.inputInt();
                System.out.print("Please type the new Number of Flat: ");
                int flatnum = ui.inputInt();

                p.updateFlatAvailability(flatTypesList.get((flatType-1)), flatnum);
                System.out.println("Successfully changed the number of flats!");
                System.out.println(p); // too long, need to change
                break;
            }
            else {
                System.out.println("No current project handled. Please register.");
            }
        }
    }

    public void changeApplicationStatus(Storage storage) {
        viewApplication(storage);
        System.out.print("Please type the Applicant ID: ");
        String ApplicantID = ui.inputString();

        for (BTOApplication app : storage.getBTOApplications().values()) {
            if(app.getApplicantID().contains(ApplicantID)) {
                List<ApplicationStatus> registrationStatusList = new ArrayList<>();
                int count=1;
                for (ApplicationStatus status : ApplicationStatus.values()) {
                    System.out.println(count+") "+status);
                    count++;
                    registrationStatusList.add(status);
                }
                System.out.print("Current Application Status: "+app.getApplicationStatus());
                System.out.print("Please choose the new status: ");
                int newstatus = ui.inputInt();
                app.setApplicationStatus(registrationStatusList.get(newstatus-1));
                break;

            }
        }
        setRegistrationStatus(RegistrationStatus.NOT_REGISTERED);
        setOfficerStatus(OfficerStatus.NEITHER);
    }
    public String getOfficerStatus(){
        return this.officerStatus.toString();
    }

    public String getRegistrationStatus(){
        return this.registrationStatus.toString();
    }

    public void setRegistrationStatus(RegistrationStatus status){
        this.registrationStatus=status;
    }

    public String getProjectAllocated() { return this.ProjectAllocated; }
    public void setProjectAllocated(String ProjectName){
        this.ProjectAllocated=ProjectName;
    }
    public void setOfficerStatus(OfficerStatus offStatus){
        this.officerStatus=offStatus;
    }

}
