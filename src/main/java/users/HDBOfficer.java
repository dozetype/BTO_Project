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

    /**
     * Showing the project list by name
     * Called in some of the methods
     * @param storage DataBase
     * @return list of projectNames
     */
    public List<String> showProjectslist(IStorage storage){
        List<String> projectNames = new ArrayList<>();
        int count = 1;
        for (Project p : storage.getProject().values()) {
            projectNames.add(p.getProjectName());
            System.out.println(count++ + ") " + p.getProjectName());
        }
        return projectNames;
    }
    /**
     * View the list of BTO project that applicable or handled by officer
     * Override and use the same method from Applicant class
     * @param st DataBase
     */
    @Override
    public void viewBTOProject(IStorage st) {
        System.out.println("1. View open project to apply as applicant");
        System.out.println("2. View my projects");
        System.out.print("Pick which project you want to view: ");
        switch (ui.inputInt()){
            case 1:
                super.viewBTOProject(st);
                break;
            case 2:
                if (projectsAllocated.isEmpty()){
                    System.out.println("No project handled");
                    return;
                }
                for(Project p : st.getProject().values()) {
                    if (projectsAllocated.contains(p.getProjectName())) {
                        System.out.println(p);
                    }
                }
                break;

            default:
                System.out.println("Unexpected value, return to main menu");
                }
    }

    /**
     * Register to join a project team
     * -Officer can join as many different projects as they want, as long as the projects they are
     *  currently inside are all closed for BTO Application
     * @param storage DataBase
     */

    public void registerToJoinProject(IStorage storage) {
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
                            System.out.println("Cannot apply. The time is overlapping with the project you are handling: " + p.getProjectName());
                            return;
                        }
                    }
                }
            }

            storage.registerProject(getUserID(), projectName);
            System.out.println("Please wait for the result. You are applying as Officer in "+projectName);
        } catch (Exception e) {
            System.out.println(e.getMessage() + " " + e.getCause());
        }
    }

    /**
     * View the list of enquiries that written by officer or from the project handled by
     * Override and use the same method from Applicant class
     * @param storage DataBase
     */
    @Override
    public void viewEnquiries(IStorage storage) {
        System.out.println("1. View my enquiries");
        System.out.println("2. View all enquiries I am handling");
        System.out.print("Pick which enquiries you want to view: ");
        switch (ui.inputInt()){
            case 1:
                super.viewEnquiries(storage);
                break;
            case 2:
                if (projectsAllocated.isEmpty()){
                    System.out.println("No project handled.");
                    return;
                }
                for(Enquiry e : storage.getEnquiries().values()) {
                    if(projectsAllocated.contains(e.getProjectName())) {
                        System.out.println(e);
                    }
                }
                break;

            default:
                System.out.println("Unexpected value, return to main menu");
        }
    }

    /**
     * Only able to view and reply to UNANSWERED Enquiries in the project handled
     * @param storage DataBase
     */
    public void replyToEnquiry(IStorage storage) {
        Map<String, Enquiry> availableEnquiries = new HashMap<>(); //Used to store unanswered relavant
        for(Enquiry e : storage.getEnquiries().values()) {
            if (projectsAllocated.contains(e.getProjectName()) && e.getReply().equals("NULL")) {
                availableEnquiries.put(e.getID(), e);
                System.out.println(e.toStringReply());
            }
        }
        if(!availableEnquiries.isEmpty()){
            System.out.print("Please choose the Enquiries ID: ");
            Enquiry toBeReplied = availableEnquiries.get(ui.inputString());
            if(toBeReplied != null) {
                System.out.println("Please write your reply: ");
                toBeReplied.setReply(ui.inputString());
                System.out.println("Thank you for your reply!");
            }
            else{
                System.out.println("Entered ID does not exist. Return to main menu.");
            }
        }
    }


    /**
     * View BTO Applications that officer applied to
     * @param storage DataBase
     */
    public void viewProjectsBTOApplication(IStorage storage) {
        System.out.println("Showing BTO Applications for project(s) you are assigned:");
        for(BTOApplication app : storage.getBTOApplications().values()) {
            if (projectsAllocated.contains(app.getProjectName())) {
                System.out.println(app);
            }
        }
        System.out.println();
    }
    /**
     * Method for creating an application receipt
     * Called in changeApplicationStatus method
     * @param storage DataBase
     * @return String of receipt
     */
    private String generateReceipt(IStorage storage, String applicantID) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String currentDate = dateFormat.format(new Date());

        StringBuilder receipt = null;
        for (BTOApplication app : storage.getBTOApplications().values()) {
            if (app.getApplicantID().equals(applicantID)) {
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
        }
        return receipt.toString();
    }
    /**
     * Method for checking the Registration Status for each project
     * @param storage DataBase
     * @return String of Registration Status
     */
    public String checkRegistrationStatus(IStorage storage) {
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

    /**
     * Method for checking the Project Handled in the ProjectList.csv and store it locally
     * @param storage DataBase
     */
    public void checkProjectsAllocated(IStorage storage) {
        for (Project p : storage.getProject().values()) {
            if (p.getProjectTeam().getOfficers().contains(getUserID())) {
                addProjectsAllocated(p.getProjectName());
            }
        }
    }

    /**
     * Method for updating the Number Of Flats
     * Called in changeApplicationStatus Method
     * @param storage,applicantID DataBase and Applicant ID
     */
    private void updateNumOfFlats(IStorage storage, String applicantID){
        for (Project p : storage.getProject().values()) {
            for (BTOApplication app : storage.getBTOApplications().values()) {
                if (projectsAllocated.contains(p.getProjectName())&& app.getApplicantID().equals(applicantID)) {
                    p.updateFlatAvailability(app.getFlatType(), (p.getUnits().get(app.getFlatType())-1));
                    System.out.println("Successfully changed the number of flats!");
                    return;
                }
            }
        }
    }

    /**
     * Method for change the application status from Successful to Booked
     * Then, update number of flats available by calling the method
     * Then, generate the receipt
     * @param storage DataBase
     */
    public void changeBTOApplicationStatus(IStorage storage) {
        viewProjectsBTOApplication(storage);
        System.out.print("Please type the Application ID: ");
        String AppID = ui.inputString();

        for (BTOApplication app : storage.getBTOApplications().values()) {
            if(app.getID().equals(AppID)) {
                if(app.getApplicationStatus()!=ApplicationStatus.SUCCESSFUL) {
                    System.out.println("This application has not been successful or withdrawn.");
                    return;
                }
                System.out.println("Current Application Status: "+app.getApplicationStatus());
                System.out.print("Do you want to change the status from Successful to Booked? Y/N: ");
                if (ui.inputString().equals("Y")){
                    app.setApplicationStatus(ApplicationStatus.BOOKED);
                    app.setOfficerInCharge(getUserID());
                    System.out.println("Application status for "+app.getApplicantID()+" changed to Booked");
                    updateNumOfFlats(storage,app.getApplicantID());
                    System.out.println(generateReceipt(storage,app.getApplicantID()));
                }
                else {
                    System.out.println("Application status for "+app.getApplicantID()+" is not changed");
                }
            }
        }
    }

    /**
     * Method for adding the Project Handled
     * @param projectName ProjectName
     */
    public void addProjectsAllocated(String projectName){
        this.projectsAllocated.add(projectName);
    }

}
