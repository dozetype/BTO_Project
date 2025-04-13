package users;
import storage.Enquiry;
import storage.*;
import ui.Messages;
import ui.Ui;

import java.util.ArrayList;
import java.util.List;

public class Applicant extends User implements IApplicant {
    Ui ui = new Ui();
//    Storage storage;
    public Applicant(List<String> userData) {
        super(userData, "Applicant");
    }

    public void viewProject(Storage st, boolean Visible){
        int count=1;
        //might need to clean a bit
        if (Visible) {
            for (Project p : st.getProject()) {
                if (p.getProjectVisibility()) {
                    System.out.print(count++ + ") ");
                    p.getListOfStrings().forEach(item -> System.out.print(item + " | ")); //TODO dk what shld be displayed
                    System.out.println();
                }
            }
        }
        else {
            for (Project p : st.getProject()) {
                System.out.print(count++ + ") ");
                p.getListOfStrings().forEach(item -> System.out.print(item + " | ")); //TODO dk what shld be displayed
                System.out.println();
            }
        }
    }

    /**
     * @param storage DataBase
     */
    public void viewEnquiries(Storage storage){
        for(Enquiry e : storage.getEnquiries().values()) {
            if(e.getAskerID().equals(getUserID())) {
                System.out.println("Your Question: " + e.getQuestion() + "\n" +"Your Reply: " + e.getReply());
            }
        }
    }

    /**
     * @param storage DataBase
     */
    public void addEnquiry(Storage storage){
        List<String> projectNames = new ArrayList<>();
        int count=1;
        for(Project p : storage.getProject()) {
            projectNames.add(p.getProjectName());
            System.out.println(count++ +") "+p.getProjectName());
        }
        try {
            System.out.print("Pick which Project you would like to add an Enquiry: ");
            String projectName = projectNames.get(ui.inputInt() - 1);
            System.out.print("Enter Your Enquiry: ");
            String question = ui.inputString();
            storage.addEnquiries(getUserID(), projectName, question);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param storage DataBase
     */
    public void removeEnquiry(Storage storage){
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
                System.out.println(e.getMessage());
            }
        }
        else{
            System.out.println("Nothing to remove");
        }
    }

}
