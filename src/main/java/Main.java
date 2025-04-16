import storage.Storage;
import system.Login;
import ui.*;
import users.*;

import java.util.List;
import java.util.Objects;

public class Main{
    List<String> loginUserData;
    User currUser;
    private Ui ui;
    private Storage storage;

    public static void main(String[] args){
        System.out.println(Messages.APPLICATION_NAME);
        new Main().run();
    }

    public void run(){
        initialize();
        loginIn();
        exit();
    }

    /**
     * Method used to Initialize
     */
    private void initialize(){
        ui = new Ui();
        storage = new Storage();
    }

    /**
     * User login, and create the type of User
     */
    private void loginIn(){
        while(!Objects.equals(ui.switchOff(), "0")) {
            Login login = new Login();
            while (loginUserData == null) {
                loginUserData = login.fetchDatabase(ui.readUserID(), ui.readPassword(), storage);
                if (loginUserData == null) {
                    System.out.println("Invalid credentials!");
                }
            }

            currUser = switch (loginUserData.get(5)){
                case "Manager" -> new HDBManager(loginUserData);
                case "Officer" -> new HDBOfficer(loginUserData);
                default -> new Applicant(loginUserData);
            };

            //MENU

            if(currUser instanceof HDBManager) {
                managerMenu();
            }
            else {
                if(currUser instanceof HDBOfficer){
                    //loading info for officer
                    ((HDBOfficer)currUser).checkProjectsAllocated(storage);
                }
                normalMenu();
            }

            loginUserData = null;//log out
        }
    }

    public void normalMenu(){ //TODO make UX better
        int choice;
        System.out.println("Hello "+currUser.getName()+", "+currUser.getUserType());
        if (currUser instanceof HDBOfficer) {System.out.println(Messages.OFFICER_MENU);}
        else  {System.out.println(Messages.APPLICANT_MENU);}
        do {
            System.out.print("\nMenu ");
            choice = ui.inputInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter filter to add: ");
                    currUser.setFilterList(ui.inputString());
                    break;
                case 2:
                    System.out.println("Current Filters: ");
                    currUser.getFilterList();
                    break;
                case 3:
                    System.out.println("Remove Filter");
                    currUser.getFilterList();
                    currUser.removeFilter(ui.inputInt());
                    break;
                case 4:
                    System.out.print("Enter New Password: ");
                    currUser.setPassword(ui.inputString());
                    storage.updateUserData(currUser.getAllUserData());
                    break;
                case 5:
                    ((Applicant)currUser).viewBTOProject(storage);
                    break;
                case 6:
                    ((Applicant)currUser).applyBTOProject(storage);
                    break;
                case 7:
                    ((Applicant)currUser).viewEnquiries(storage);
                    break;
                case 8:
                    ((Applicant)currUser).addEnquiry(storage);
                    break;
                case 9:
                    ((Applicant)currUser).removeEnquiry(storage);
                    break;
                case 10:
                    if(currUser instanceof HDBOfficer) {
                        ((HDBOfficer)currUser).registerToJoinProject(storage);
                        break;
                    }
                case 11:
                    if(currUser instanceof HDBOfficer) {
                        System.out.println("Your registration status is: "+((HDBOfficer) currUser).checkRegistrationStatus(storage));
                        break;
                    }
                case 12:
                    if(currUser instanceof HDBOfficer) {
                        ((HDBOfficer)currUser).replyToEnquiry(storage);
                        break;
                    }
                case 13:
                    if(currUser instanceof HDBOfficer) {
                        ((HDBOfficer)currUser).changeBTOApplicationStatus(storage);
                        break;
                    }
            }
        }while(choice != 0);
    }

    public void managerMenu(){
        int choice;
        System.out.println("Hello "+currUser.getName()+"");
        System.out.println(Messages.MANAGER_MENU);
        do {
            choice = ui.inputInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter filter to add: ");
                    currUser.setFilterList(ui.inputString());
                    break;
                case 2:
                    System.out.println("Current Filters: ");
                    currUser.getFilterList();
                    break;
                case 3:
                    System.out.println("Remove Filter");
                    currUser.getFilterList();
                    currUser.removeFilter(ui.inputInt());
                    break;
                case 4:
                    System.out.print("Enter New Password: ");
                    currUser.setPassword(ui.inputString());
                    storage.updateUserData(currUser.getAllUserData());
                    break;
                case 5:
                	((HDBManager)currUser).createProject(storage);
                    break;
                case 6:
                	((HDBManager)currUser).editProject(storage);
                	break;
                case 7:
                	((HDBManager)currUser).deleteProject(storage);
                    break;
                case 8:
                	((HDBManager)currUser).viewAllProjects(storage);
                    break;
                case 9:
                	((HDBManager)currUser).viewCreatedProject(storage);
                    break;
                case 10:
                	((HDBManager)currUser).toggleProjectVisibility(storage);
                	break;
                case 11:
                	((HDBManager)currUser).viewHDBOfficerRegistration(storage);
                	break;
                case 12:
                	((HDBManager)currUser).approveHDBOfficerRegistration(storage);
                	break;
                case 13:
                	((HDBManager)currUser).rejectHDBOfficerRegistration(storage);
                	break;
                case 14:
                	((HDBManager)currUser).approveApplicantApplication(storage);
                	break;
                case 15:
                	((HDBManager)currUser).rejectApplicantApplication(storage);
                	break;
                case 16:
                	((HDBManager)currUser).approveWithdrawalRequest(storage);
                	break;
                case 17:
                	((HDBManager)currUser).rejectWithdrawalRequest(storage);  
                	break;
                case 18:
                	((HDBManager)currUser).generateApplicantReport(storage);
                	break;
                case 19:
                	((HDBManager)currUser).viewEnquiries(storage);
                	break;
                case 20:
                	((HDBManager)currUser).replyToEnquiry(storage);      
                	break;
            }
        }while(choice != 0);
    

    }


    private void exit(){
        storage.close(); //Override UserList.csv
        System.exit(0);
    }

}