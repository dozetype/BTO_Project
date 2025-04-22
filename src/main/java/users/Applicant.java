package users;
import storage.*;
import ui.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Applicant extends User implements IApplicant {
    Ui ui = new Ui();

    public Applicant(List<String> userData) {
        super(userData, "Applicant");
    }

    public void viewBTOProject(IStorage st){
        //Stores all the BTO project Names that User have applied for
        List<String> appliedBTOProject = new ArrayList<>();
        for(BTOApplication a : st.getBTOApplications().values()){
            if(a.getApplicantID().equals(getUserID())){
                appliedBTOProject.add(a.getProjectName());
            }
        }
        System.out.println("Viewing Open Projects:");
        for(Project p : st.getFilteredProjects(getFilters())){
            if((p.getProjectVisibility() && p.currentlyOpenOrClosed()) || (appliedBTOProject.contains(p.getProjectName()))){
                if(!"THREE_ROOM".equals(getFilters().get("FLAT_TYPE"))) {
                    if ((getMaritalStatus() == MaritalStatus.SINGLE && getAge() >= 35) || (getMaritalStatus() == MaritalStatus.MARRIED && getAge() >= 21)) {
                        System.out.println(p.getProjectName() +" "+p.getNeighbourhood()+", 2 ROOMS, Price: "+p.getPrices().get(FlatType.TWO_ROOM)+", Available units: "+p.getUnits().get(FlatType.TWO_ROOM)+", Opening Date: "+p.getOpeningDateString()+", Closing Date: "+p.getClosingDateString());
                    }
                }
                if(!"TWO_ROOM".equals(getFilters().get("FLAT_TYPE"))) {
                    if (getMaritalStatus() == MaritalStatus.MARRIED && getAge() >= 21) {
                        System.out.println(p.getProjectName()+" "+p.getNeighbourhood()+", 3 ROOMS, Price:"+p.getPrices().get(FlatType.THREE_ROOM)+", Available units: "+p.getUnits().get(FlatType.THREE_ROOM)+", Opening Date: "+p.getOpeningDateString()+", Closing Date: "+ p.getClosingDateString());
                    }
                }
            }
        }
    }

    public void applyBTOProject(IStorage st) {
        for(BTOApplication application : st.getBTOApplications().values()){
            if(application.getApplicantID().equals(getUserID()) && (!application.getApplicationStatus().equals(ApplicationStatus.WITHDRAWN) && !application.getApplicationStatus().equals(ApplicationStatus.UNSUCCESSFUL))){
                System.out.println("You have already applied for a Project.");
                return;
            }
        }
        int count=1;
        //check time, check marital status, check visibility, check if officer is in project
        List<List<String>> applicableUnits = new ArrayList<>(); //store data of options
        List<String> unit =  new ArrayList<>();
        System.out.println("Which Project would you like to apply? (0 to quit)");
        for(Project p : st.getFilteredProjects(getFilters())){
            if(p.getProjectTeam().getOfficers().contains(getUserID())){
                continue; //check if he is an officer
            }
            if(p.getProjectVisibility() && p.currentlyOpenOrClosed()){ //if visible and within timeframe
                if((getMaritalStatus()==MaritalStatus.SINGLE && getAge()>=35) || (getMaritalStatus()==MaritalStatus.MARRIED && getAge()>=21)){
                    System.out.println(count+")"+p.getProjectName()+" "+p.getNeighbourhood()+", 2 ROOMS, Price: "+p.getPrices().get(FlatType.TWO_ROOM)+", Available units: "+ p.getUnits().get(FlatType.TWO_ROOM));
                    unit.add(p.getProjectName());
                    unit.add(String.valueOf(p.getPrices().get(FlatType.TWO_ROOM)));
                    unit.add("TWO_ROOM");
                    applicableUnits.add(unit);
                    count++;
                    unit =  new ArrayList<>(); //clear
                }
                if(getMaritalStatus()==MaritalStatus.MARRIED && getAge()>=21){
                    System.out.println(count+")"+p.getProjectName()+" "+p.getNeighbourhood()+", 3 ROOMS, Price: "+p.getPrices().get(FlatType.THREE_ROOM)+", Available units: "+ p.getUnits().get(FlatType.THREE_ROOM));
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
        } catch(IndexOutOfBoundsException e){ //out of index
            System.out.println(e.getMessage());
        }
    }

    public void viewBTOApplication(IStorage st) {
        for(BTOApplication application : st.getBTOApplications().values()){
            if(application.getApplicantID().equals(getUserID())){
                System.out.println(application);
            }
        }
    }

    public void viewEnquiries(IStorage storage){
        for(Enquiry e : storage.getEnquiries().values()) {
            if(e.getAskerID().equals(getUserID())) {
                System.out.println("Your Question: " + e.getQuestion() + "\n" +"Your Reply: " + e.getReply());
            }
        }
    }

    public void addEnquiry(IStorage storage){
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
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeEnquiry(IStorage storage){
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
            }catch (IndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            }
        }
        else{
            System.out.println("Nothing to remove");
        }
    }

    public void editEnquiry(IStorage st){
        Map<Integer, Enquiry> editableEnquiries = new HashMap<>();
        int count=1;
        for(Enquiry e : st.getEnquiries().values()) {
            if(e.getAskerID().equals(getUserID()) && e.getReply().equals("NULL")) {
                editableEnquiries.put(count, e);
                System.out.println(count++ +")"+ "Your Question: " + e.getQuestion());
            }
        }

        try {
            if (!editableEnquiries.isEmpty()) {
                System.out.println("Select an Enquiry");
                count = ui.inputInt();
                System.out.println("Enter Your NEW Enquiry: ");
                editableEnquiries.get(count).setQuestion(ui.inputString());
            } else {
                System.out.println("Nothing to edit. Exiting.");
            }
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    public void withdrawBTOApplication(IStorage st){
        for(BTOApplication application : st.getBTOApplications().values()) {
            if(application.getApplicantID().equals(getUserID()) &&
            (application.getApplicationStatus().equals(ApplicationStatus.PENDING)||application.getApplicationStatus().equals(ApplicationStatus.SUCCESSFUL)||application.getApplicationStatus().equals(ApplicationStatus.BOOKED))) {
                System.out.println("Withdrawing from " + application.getProjectName()+", "+application.getFlatType());
                application.setApplicationStatus(ApplicationStatus.WITHDRAWING);
                return;
            }
        }
        System.out.println("No BTO Application found that can be withdrawn");
    }
}