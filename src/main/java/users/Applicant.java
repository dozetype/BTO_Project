package users;
import storage.Enquiry;
import storage.*;
import ui.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Applicant extends User implements IApplicant {
    Ui ui = new Ui();
//    Storage storage;
    public Applicant(List<String> userData) {
        super(userData, "Applicant");

    }

    /**
     * User can see project listing if its open and its visible
     * If User have applied before they should still be able to see the listing even after Time Frame
     * @param st
     */
    public void viewBTOProject(Storage st){
        //Stores all the BTO project Names that User have applied for
        List<String> appliedBTOProject = new ArrayList<>();
        for(BTOApplication a : st.getBTOApplications().values()){
            if(a.getApplicantID().equals(getUserID())){
                appliedBTOProject.add(a.getProjectName());
            }
        }
        System.out.println("Viewing Open Projects:");
        for(Project p : st.getProject().values()){
            if((p.getProjectVisibility() && p.currentlyOpenOrClosed()) || (appliedBTOProject.contains(p.getProjectName()))){
                if((getMaritalStatus()==MaritalStatus.SINGLE && getAge()>=35) || (getMaritalStatus()==MaritalStatus.MARRIED && getAge()>=21)){
                    System.out.println(p.getProjectName()+" 2 ROOMS, Price: "+p.getPrices().get(FlatType.TWO_ROOM)+", Available units: "+ p.getUnits().get(FlatType.TWO_ROOM));
                }
                else
                    System.out.println(p.getProjectName()+": Not able to view 2 ROOMS");
                if(getMaritalStatus()==MaritalStatus.MARRIED && getAge()>=21){
                    System.out.println(p.getProjectName()+" 3 ROOMS, Price: "+p.getPrices().get(FlatType.THREE_ROOM)+", Available units: "+ p.getUnits().get(FlatType.THREE_ROOM));
                }
                else
                    System.out.println(p.getProjectName()+": Not able to view 3 ROOMS");
            }
        }
    }

    /**
     * Only able to View and Apply for BTO Project if they have not applied or if their previous application was Unsuccessful or Withdrawn
     * @param st
     */
    public void applyBTOProject(Storage st) {
        for(BTOApplication application : st.getBTOApplications().values()){
            if(application.getApplicantID().equals(getUserID()) && (!application.getApplicationStatus().equals(ApplicationStatus.WITHDRAWN) || !application.getApplicationStatus().equals(ApplicationStatus.UNSUCCESSFUL))){
                System.out.println("You have already applied for a Project.");
                return;
            }
        }
        int count=1;
        //check time, check marital status, check visibility, check if officer is in project
        List<List<String>> applicableUnits = new ArrayList<>(); //store data of options
        List<String> unit =  new ArrayList<>();
        System.out.println("Which Project would you like to apply? (0 to quit)");
        for(Project p : st.getProject().values()){
            if(p.getProjectTeam().getOfficers().contains(getUserID())){
                continue; //check if he is an officer
            }
            if(p.getProjectVisibility() && p.currentlyOpenOrClosed()){ //if visible and within timeframe
                if((getMaritalStatus()==MaritalStatus.SINGLE && getAge()>=35) || (getMaritalStatus()==MaritalStatus.MARRIED && getAge()>=21)){
                    System.out.println(count+")"+p.getProjectName()+" 2 ROOMS, Price: "+p.getPrices().get(FlatType.TWO_ROOM)+", Available units: "+ p.getUnits().get(FlatType.TWO_ROOM));
                    unit.add(p.getProjectName());
                    unit.add(String.valueOf(p.getPrices().get(FlatType.TWO_ROOM)));
                    unit.add("TWO_ROOM");
                    applicableUnits.add(unit);
                    count++;
                    unit =  new ArrayList<>(); //clear
                }
                if(getMaritalStatus()==MaritalStatus.MARRIED && getAge()>=21){
                    System.out.println(count+")"+p.getProjectName()+" 3 ROOMS, Price: "+p.getPrices().get(FlatType.THREE_ROOM)+", Available units: "+ p.getUnits().get(FlatType.THREE_ROOM));
                    unit.add(p.getProjectName());
                    unit.add(String.valueOf(p.getPrices().get(FlatType.THREE_ROOM)));
                    unit.add("THREE_ROOM");
                    applicableUnits.add(unit);
                    count++;
                    unit =  new ArrayList<>(); //clear
                }
            }
        }
        try{
            if(!applicableUnits.isEmpty()){
                unit = applicableUnits.get(ui.inputInt()-1);
                st.addBTOApplication(getUserID(), unit.get(0), unit.get(1), unit.get(2));
                System.out.println("Thank you! you are applying for: "+unit.get(0));
            }
            else{
                System.out.println("No applicable units found");
            }
        } catch(Exception e){ //out of index
            System.out.println(e.getMessage());
        }
    }

    /**
     *View All BTO Applications made by user throughout all Projects
     * @param st DataBase
     */
    public void viewApplication(Storage st) {
        for(BTOApplication application : st.getBTOApplications().values()){
            if(application.getApplicantID().equals(getUserID())){
                System.out.println(application); //TODO may need to change the UX
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
        for(Project p : storage.getProject().values()) {
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
     * Can Only Delete the Enquiry if it can has not been replied
     * @param storage DataBase
     */
    public void removeEnquiry(Storage storage){
        List<String> enquiryIDList = new ArrayList<>();
        int count=1;
        for(Enquiry e : storage.getEnquiries().values()) {
            if(e.getAskerID().equals(getUserID()) && e.getReply().equals("NULL")) {
                enquiryIDList.add(e.getID());
                System.out.println(count++ +")"+ "Your Question: " + e.getQuestion());
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

    /**
     * User can only edit their enquiry if it hasn't been replied
     * @param st
     */
    public void editEnquiry(Storage st){
        Map<Integer, Enquiry> editableEnquiries = new HashMap<>();
        int count=1;
        for(Enquiry e : st.getEnquiries().values()) {
            if(e.getAskerID().equals(getUserID()) && e.getReply().equals("NULL")) {
                editableEnquiries.put(count, e);
                System.out.println(count++ +")"+ "Your Question: " + e.getQuestion());
            }
        }

        if(!editableEnquiries.isEmpty()) {
            System.out.println("Select an Enquiry");
            count = ui.inputInt();
            System.out.println("Enter Your NEW Enquiry: ");
            editableEnquiries.get(count).setQuestion(ui.inputString());
        }
        else{
            System.out.println("Nothing to edit. Exiting.");
        }
    }

}
