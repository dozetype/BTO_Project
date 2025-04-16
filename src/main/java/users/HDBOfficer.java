package users;

import storage.*;

import java.text.SimpleDateFormat;
import java.util.*;

public class HDBOfficer extends Applicant {
    private List<String> projectsAllocated = new ArrayList<>(); //find out Project allocated and Registration from ProjectTeam

    public HDBOfficer(List<String> userData) {
        super(userData);
        setUserType("Officer"); //Override "Applicant"
    }

    public List<String> showProjectslist(Storage storage){
        List<String> projectNames = new ArrayList<>();
        int count = 1;
        for (Project p : storage.getProject().values()) {
            projectNames.add(p.getProjectName());
            System.out.println(count++ + ") " + p.getProjectName());
        }
        return projectNames;
    }

    /**
     * Register to join a project team
     * -Officer can join as many different projects as they want, as long as the projects they are
     *  currently inside are all closed for BTO Application
     * @param storage DataBase
     */

    public void registerToJoinProject(Storage storage) {
        List<String> projectNames = showProjectslist(storage);
        try {
            System.out.print("Pick which Project you would like to register as Officer: ");
            String projectName = projectNames.get(ui.inputInt() - 1);
            for(BTOApplication application : storage.getBTOApplications().values()) {
                if (application.getApplicantID().equals(getUserID()) && application.getProjectName().equals(projectName)) {
                    System.out.println("You have already applied for this project as Applicant.");
                    return;
                }
            }
            for (Project p : storage.getProject().values()) {
                if (p.getProjectTeam().getOfficers().contains(getUserID())||p.getProjectTeam().getOfficersApplying().contains(getUserID())) {
                    if (p.getProjectName().equals(projectName)&&p.getProjectTeam().getOfficers().contains(getUserID())) {
                        System.out.println("You are an Officer of this project.");
                        return;
                    }
                    if (p.getProjectName().equals(projectName)&&p.getProjectTeam().getOfficersApplying().contains(getUserID())) {
                        System.out.println("You have already registering for this project.");
                        return;
                    }
                    for (Project q : storage.getProject().values()) {
                        if (q.getProjectName().equalsIgnoreCase(projectName)&&q.overlapping(p)){
                            System.out.println("Cannot apply. The time is overlapping with " + p.getProjectName());
                            return;
                        }
                    }
                }
            }

            storage.registerProject(getUserID(), projectName);
            System.out.println("Please wait for the result. You are applying as Officer in "+projectName);
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
            if(projectsAllocated.contains(e.getProjectName())) {
                System.out.println(e);
            }
        }
    }

    /**
     * Only able to view and reply to UNANSWERED Enquiries
     * @param storage DataBase
     */
    public void replyToEnquiry(Storage storage) {
        Map<String, Enquiry> availableEnquiries = new HashMap<>(); //Used to store unanswered relavant
        for(Enquiry e : storage.getEnquiries().values()) {
                if(projectsAllocated.contains(e.getProjectName())&& e.getReply().equals("NULL")) {
                    availableEnquiries.put(e.getID(), e);
                    System.out.println(e);
                }
            }
        if(!availableEnquiries.isEmpty()){
            System.out.println("Please choose the Enquiries ID: ");
            Enquiry toBeReplied = availableEnquiries.get(ui.inputString());
            if(toBeReplied != null) {
                System.out.println("Please write your reply: ");
                toBeReplied.setReply(ui.inputString());
                System.out.println("Thank you for your reply!");
            }
            else{
                System.out.println("Entered ID does not exist. Exiting.");
            }
        }
    }


    /**
     * View BTO Applications of all project Officer is in
     * @param storage
     */
    public void viewProjectsBTOApplication(Storage storage) {
        for(BTOApplication app : storage.getBTOApplications().values()) {
            if (projectsAllocated.contains(app.getProjectName())) {
                System.out.println(app);
            }
        }
    }

    public String generateReceipt(Storage storage, String applicantID) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String currentDate = dateFormat.format(new Date());

        StringBuilder receipt = null;
        for (BTOApplication app : storage.getBTOApplications().values()) {
            receipt = new StringBuilder();
            List<String> applicant = storage.getUserData(applicantID);
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

        return receipt.toString();
    }

    public String checkRegistrationStatus(Storage storage) {
        List<String> projectNames = showProjectslist(storage);
        System.out.print("Pick which project you would like to see the registration status: ");
        String projectName = projectNames.get(ui.inputInt() - 1);
        for (Project p : storage.getProject().values()) {
            if (p.getProjectTeam().getOfficers().contains(getUserID()) && p.getProjectName().equals(projectName)) {
                return RegistrationStatus.SUCCESSFUL.toString();
            } else if (p.getProjectTeam().getOfficersApplying().contains(getUserID()) && p.getProjectName().equals(projectName)) {
                return RegistrationStatus.PENDING.toString();
            } else if (p.getProjectTeam().getOfficersRejected().contains(getUserID()) && p.getProjectName().equals(projectName)) {
                return RegistrationStatus.UNSUCCESSFUL.toString();
            }
        }
        return RegistrationStatus.NOT_REGISTERED.toString();
    }

    public void checkProjectsAllocated(Storage storage) {
        for (Project p : storage.getProject().values()) {
            if (p.getProjectTeam().getOfficers().contains(getUserID())) {
                addProjectsAllocated(p.getProjectName());
            }
        }
    }

    public void updateNumOfFlats(Storage storage, String applicantID){
        for (Project p : storage.getProject().values()) {
            for (BTOApplication app : storage.getBTOApplications().values()) {
                if (projectsAllocated.contains(p.getProjectName())&& app.getApplicantID().equals(applicantID)) {
                    System.out.println(p.getUnits());
                    p.updateFlatAvailability(app.getFlatType(), (p.getUnits().get(app.getFlatType())-1));
                    System.out.println("Successfully changed the number of flats!");
                    System.out.println(p.getUnits());
                    return;
                }
            }
        }
    }

    public void changeBTOApplicationStatus(Storage storage) {
        viewProjectsBTOApplication(storage);
        System.out.print("Please type the Applicant ID: ");
        String ApplicantID = ui.inputString();

        for (BTOApplication app : storage.getBTOApplications().values()) {
            if(app.getApplicantID().contains(ApplicantID)) { //Check the applicantID, need to change to BTOApplication ID
                System.out.println("Current Application Status: "+app.getApplicationStatus());
                System.out.print("Do you want to change the status from Successful to Booked? Y/N: ");
                if (ui.inputString().equals("Y")){
                    app.setApplicationStatus(ApplicationStatus.BOOKED);
                    app.setOfficerInCharge(getUserID());
                    System.out.println("Application status for "+app.getApplicantID()+" changed to Booked");
                    updateNumOfFlats(storage,ApplicantID);
                    System.out.println(generateReceipt(storage,ApplicantID));
                }
                else {
                    System.out.println("Application status for "+app.getApplicantID()+" is not changed");
                }
            }
        }
    }

    public void addProjectsAllocated(String projectName){
        this.projectsAllocated.add(projectName);
    }

}
