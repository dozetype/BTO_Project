package users;

import storage.Enquiry;
import storage.*;
import ui.Messages;

import java.util.ArrayList;
import java.util.List;

public class HDBOfficer extends Applicant {
    private OfficerStatus officerStatus=OfficerStatus.NEITHER;
    private RegistrationStatus registrationStatus= RegistrationStatus.NOT_REGISTERED;
    private String ProjectAllocated="Acacia Breeze"; //find out Project allocated and Registration from ProjectTeam
    public HDBOfficer(List<String> userData) {
        super(userData);
        setUserType("Officer");
    }


    /**
     * @param storage DataBase
     */
    public void registerToJoinProject(Storage storage) {
        if (getOfficerStatus().equals("NEITHER")) {
            List<String> projectNames = new ArrayList<>();
            int count = 1;
            for (Project p : storage.getProject()) {
                projectNames.add(p.getProjectName());
                System.out.println(count++ + ") " + p.getProjectName());
            }
            try {
                System.out.print("Pick which Project you would like to register as Officer: ");
                String projectName = projectNames.get(ui.inputInt() - 1);
//                storage.registerProject(getUserID(), projectName);
                System.out.println("Please wait for the result. You are applying as Officer in "+projectName);
                setRegistrationStatus(RegistrationStatus.PENDING);
                System.out.println(storage.getProject());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
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
    public String getOfficerStatus(){
        return this.officerStatus.toString();
    }

    public String getRegistrationStatus(){
        return this.registrationStatus.toString();
    }

    public void setRegistrationStatus(RegistrationStatus status){
        this.registrationStatus=status;
    }
}
