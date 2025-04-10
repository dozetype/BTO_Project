package users;

import storage.Enquiry;
import storage.Storage;
import ui.Messages;

import java.util.ArrayList;
import java.util.List;

public class HDBOfficer extends Applicant {
    private OfficerStatus officerStatus=OfficerStatus.NEITHER;
    private RegistrationStatus registrationStatus= RegistrationStatus.NOT_REGISTERED;
    private String ProjectAllocated="Acacia Breeze";
    public HDBOfficer(List<String> userData, Storage storage) {
        super(userData, storage);
        setUserType("Officer");
    }

    @Override
    public void menu() {
        int choice;
        do {
            System.out.println("Hello "+getName()+", "+getUserType()+"\n"+ Messages.OFFICER_MENU);
            choice = ui.inputInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter filter to add: ");
                    setFilterList(ui.inputString());
                    break;
                case 2:
                    System.out.println("Current Filters: ");
                    getFilterList();
                    break;
                case 3:
                    System.out.println("Remove Filter");
                    getFilterList();
                    removeFilter(ui.inputInt());
                    break;
                case 4:
                    System.out.print("Enter New Password: ");
                    setPassword(ui.inputString());
                    storage.updateUserData(getAllUserData());
                    break;
//                case 5:
//                    super.viewProject();
//                    break;
                case 6:
                    viewEnquiries();
                    break;
                case 7:
                    super.addEnquiry();
                    break;
                case 8:
                    super.removeEnquiry();
                    break;
                case 9:
                    registerToJoinProject();
                    break;
                case 10:
                    System.out.println("Your officer registration status: "+ getRegistrationStatus());
                    break;
                case 11:
                    replyToEnquiry();

            }
        }while(choice < 12);
    }

    private void registerToJoinProject() {
        if (getOfficerStatus().equals("NEITHER")) {
            List<String> projectNames = new ArrayList<>();
            int count = 1;
            for (String s : storage.getProject().keySet()) {
                projectNames.add(s);
                System.out.println(count++ + ") " + s);
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

    @Override
    public void viewEnquiries(){
        for(Enquiry e : storage.getEnquiries().values()) {
            if(e.getProjectName().equals(ProjectAllocated)) {
                System.out.println(e);
            }
        }
    }

    private void replyToEnquiry() {
        //TODO only be able to view enq with unanswered qns
        viewEnquiries();
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
    private String getOfficerStatus(){
        return this.officerStatus.toString();
    }

    private String getRegistrationStatus(){
        return this.registrationStatus.toString();
    }

    private void setRegistrationStatus(RegistrationStatus status){
        this.registrationStatus=status;
    }
}
