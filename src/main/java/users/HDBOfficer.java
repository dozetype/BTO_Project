package users;

import storage.*;

import java.text.SimpleDateFormat;
import java.util.*;

public class HDBOfficer extends Applicant {
    private OfficerStatus officerStatus;
    private RegistrationStatus registrationStatus;
    private List<String> projectsAllocated = new ArrayList<>(); //find out Project allocated and Registration from ProjectTeam

    public HDBOfficer(List<String> userData) {
        super(userData);
        setUserType("Officer"); //Override "Applicant"
    }


    /**
     * Register to join a project team
     * -Officer can join as many different projects as they want, as long as the projects they are
     *  currently inside are all closed for BTO Application
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
        for(String partOfProject : projectsAllocated) {
            for(Enquiry e : storage.getEnquiries().values()) {
                if(e.getProjectName().equals(partOfProject)) {
                    System.out.println(e);
                }
            }
        }
    }

    /**
     * Only able to view and reply to UNANSWERED Enquiries
     * @param storage DataBase
     */
    public void replyToEnquiry(Storage storage) {
        //TODO only be able to view enq with unanswered qns
        Map<String, Enquiry> availableEnquiries = new HashMap<>(); //Used to store unanswered relavant
        for(Enquiry e : storage.getEnquiries().values()) {
            for(String partOfProject : projectsAllocated) {
                if(e.getProjectName().equals(partOfProject) && e.getReply().equals("NULL")) {
                    availableEnquiries.put(e.getID(), e);
                    System.out.println(e);
                }
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
            for(String partOfProject : projectsAllocated) {
                if (app.getProjectName().equals(partOfProject)) {
                    System.out.println(app);
                }
            }
        }
    }

    public String generateReceipt(Storage storage) {
        viewProjectsBTOApplication(storage);
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
                setProjectsAllocated(p.getProjectName());
                setRegistrationStatus(RegistrationStatus.SUCCESSFUL);
                setOfficerStatus(OfficerStatus.OFFICER);
            }
            else if(p.getProjectTeam().getOfficersApplying().contains(getUserID()) && projectsAllocated.isEmpty()) {
                setRegistrationStatus(RegistrationStatus.PENDING);
                setOfficerStatus(OfficerStatus.NEITHER);
            }
        }
        if(projectsAllocated.isEmpty()) {
            setRegistrationStatus(RegistrationStatus.NOT_REGISTERED);
            setOfficerStatus(OfficerStatus.NEITHER);
        }
    }

    //TODO Changed projectAllocated
//    public void updateNumOfFlats(Storage storage){
//        for (Project p : storage.getProject().values()) {
//            if (p.getProjectName().equals(projectsAllocated)) {
//                System.out.println(p); // too long, need to change
//                System.out.println(p.getUnits());
//                int count=1;
//                List<String> flatTypesList = new ArrayList<>();
//                for (Map.Entry<FlatType, Integer> flatTypes : p.getUnits().entrySet()){
//                    System.out.printf("%d) %-10s (Current value: %d)%n",
//                            count++,
//                            flatTypes.getKey(),
//                            flatTypes.getValue());
//                    flatTypesList.add(flatTypes.getKey().toString());
//                }
//
//                System.out.print("Please choose the Flat Type: ");
//                int flatType = ui.inputInt();
//                System.out.print("Please type the new Number of Flat: ");
//                int flatNum = ui.inputInt();
//
//                p.updateFlatAvailability(flatTypesList.get((flatType-1)), flatNum);
//                System.out.println("Successfully changed the number of flats!");
//                System.out.println(p); // too long, need to change
//                break;
//            }
//            else {
//                System.out.println("No current project handled. Please register.");
//            }
//        }
//    }

    public void changeApplicationStatus(Storage storage) {
        viewProjectsBTOApplication(storage);
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

    public List<String> getProjectsAllocated() { return this.projectsAllocated; }
    public void setProjectsAllocated(String projectName){
        this.projectsAllocated.add(projectName);
    }
    public void setOfficerStatus(OfficerStatus offStatus){
        this.officerStatus=offStatus;
    }

}
