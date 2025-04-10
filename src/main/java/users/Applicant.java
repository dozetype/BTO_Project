package users;
import storage.Enquiry;
import storage.Storage;
import ui.Messages;
import ui.Ui;

import java.util.ArrayList;
import java.util.List;

public class Applicant extends User {
    Ui ui = new Ui();
    Storage storage;
    public Applicant(List<String> userData, Storage storage) {
        super(userData, "Applicant");
        this.storage = storage;
    }

    @Override
    public void menu(){
        int choice;
        do {
            System.out.println("Hello "+getName()+", "+getUserType()+"\n"+Messages.APPLICANT_MENU);
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
                case 5:
                    viewProject();
                    break;
                case 6:
                    viewEnquiries();
                    break;
                case 7:
                    addEnquiry();
                    break;
                case 8:
                    removeEnquiry();
                    break;
            }
        }while(choice < 10);
    }

    protected void viewProject(){
        int count=1;
        for (ArrayList i : storage.getProject().values()) {
            System.out.print(count++ +") ");
            i.forEach(item -> System.out.print(item + " | "));
        }
        System.out.println();
    }

    protected void viewEnquiries(){
        for(Enquiry e : storage.getEnquiries().values()) {
            if(e.getAskerID().equals(getUserID())) {
                System.out.println("Your Question: " + e.getQuestion() + "\n" +"Your Reply: " + e.getReply());
            }
        }
    }

    protected void addEnquiry(){
        List<String> projectNames = new ArrayList<>();
        int count=1;
        for(String s : storage.getProject().keySet()) {
            projectNames.add(s);
            System.out.println(count++ +") "+s);
        }
        try {
            System.out.print("Pick which Project you would like to add an Enquiry: ");
            String projectName = projectNames.get(ui.inputInt() - 1);
            System.out.print("Enter Your Enquiry: ");
            String question = ui.inputString();
            storage.addEnquiries(getUserID(), projectName, question);
        } catch (Exception e) {
            System.out.println(e.getMessage());;
        }
    }

    protected void removeEnquiry(){
        List<String> enquiryIDList = new ArrayList<>();
        int count=1;
        for(Enquiry e : storage.getEnquiries().values()) {
            if(e.getAskerID().equals(getUserID())) {
                enquiryIDList.add(e.getID());
                System.out.println(count++ +")\n"+ "Your Question: " + e.getQuestion() + "\n" +"Your Reply: " + e.getReply());
            }
        }
        if(!enquiryIDList.isEmpty()) {
            try{
                System.out.print("Pick which Enquiry you would like to remove: ");
                String enquiryID = enquiryIDList.get(ui.inputInt() - 1);
                storage.removeEnquiries(enquiryID);
            }catch (Exception e) {
                System.out.println(e.getMessage());;
            }
        }
        else{
            System.out.println("Nothing to remove");
        }
    }

}
