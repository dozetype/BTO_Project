
package users;

import storage.ApplicationStatus;
import storage.BTOApplication;
import storage.Enquiry;
import storage.FlatType;
import storage.Project;
import storage.Storage;
import storage.ProjectTeam;
import ui.Ui;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class HDBManager extends User 
{
    Ui ui = new Ui();
//    private Storage storage;
    
    public HDBManager(List<String> userData) 
    {
        super(userData, "Manager");
    }
    
    /*
     * Can only be handling one project within an application period (from
application opening date, inclusive, to application closing date,
inclusive)
     */
    public void createProject(Storage storage) 
    {
        System.out.print("Enter Project Name: ");
        String projectName = ui.inputString();
        
        System.out.print("Enter Neighborhood: ");
        String neighborhood = ui.inputString();
       
        String flatType = "TWO_ROOM";
        
        System.out.print("Enter Number of Units for " + flatType + ": ");
        int numUnits = ui.inputInt();
        
        System.out.print("Enter Selling Price for " + flatType + ": ");
        int sellingPrice = ui.inputInt();
        
        String flatType2 = "THREE_ROOM";
        
        System.out.print("Enter Number of Units for " + flatType2 + ": ");
        int numUnits2 = ui.inputInt();
        
        System.out.print("Enter Selling Price for " + flatType2 + ": ");
        int sellingPrice2 = ui.inputInt();
        
        System.out.print("Enter Application Opening Date (dd/MM/yyyy): ");
        String openingDate = ui.inputString();
        
        System.out.print("Enter Application Closing Date (dd/MM/yyyy): ");
        String closingDate = ui.inputString();
        
        System.out.print("Enter Visibility (0 for Not visible, 1 for Visible): ");
        int visibilityInput = ui.inputInt();
        boolean visibility = (visibilityInput == 1);

        String[] project = new String[] 
        {
            projectName, 
            neighborhood, 
            flatType, 
            String.valueOf(numUnits), 
            String.valueOf(sellingPrice), 
            flatType2, 
            String.valueOf(numUnits2), 
            String.valueOf(sellingPrice2), 
            openingDate, 
            closingDate, 
            getUserID(), 
            "10", 
            "", 
            "", 
            String.valueOf(visibility)
        };
        
        Project projectAdding = new Project(project);
        storage.addProject(projectAdding);
        System.out.println("Project " + projectName + " created");
    }


    public void editProject(Storage storage) 
    {
        System.out.println("Enter Project Name to edit project: ");
        String projectName = ui.inputString(); 
        Project project = storage.getProjectByName(projectName);
        
        if (project != null) 
        {
            System.out.println("Editing Project: " + project.getProjectName());
            System.out.println("Which category would you like to edit?");
            System.out.println("1) Project Name");
            System.out.println("2) Neighborhood");
            System.out.println("3) Number of Units for Type 1");
            System.out.println("4) Selling Price for Type 1");
            System.out.println("5) Number of Units for Type 2");
            System.out.println("6) Selling Price for Type 2");
            System.out.println("7) Application Opening Date");
            System.out.println("8) Application Closing Date");
            System.out.println("9) Manager");
            System.out.println("10) Number of Officer Slots");
            System.out.println("11) Officers");
            System.out.println("12) Officers Applying");
            System.out.println("Note: Select Toggle Visibility to Change Visibility");

            int choice = ui.inputInt();
            List<String> updatedData = new ArrayList<>(project.getListOfStrings());

            switch (choice) 
            {
                case 1:
                    System.out.print("Enter new Project Name: ");
                    updatedData.set(0, ui.inputString()); 
                    break;
                case 2:
                    System.out.print("Enter new Neighborhood: ");
                    updatedData.set(1, ui.inputString());  
                    break;
                case 3:
                    System.out.print("Enter new Number of Units for Type 1: ");
                    updatedData.set(3, ui.inputString());  
                    break;
                case 4:
                    System.out.print("Enter new Selling Price for Type 1: ");
                    updatedData.set(4, ui.inputString());  
                    break;
                case 5:
                    System.out.print("Enter new Number of Units for Type 2: ");
                    updatedData.set(6, ui.inputString());  
                    break;
                case 6:
                    System.out.print("Enter new Selling Price for Type 2: ");
                    updatedData.set(7, ui.inputString());  
                    break;
                case 7:
                    System.out.print("Enter new Application Opening Date (dd/MM/yyyy): ");
                    updatedData.set(8, ui.inputString());  
                    break;
                case 8:
                    System.out.print("Enter new Application Closing Date (dd/MM/yyyy): ");
                    updatedData.set(9, ui.inputString());  
                    break;
                case 9:
                    System.out.print("Enter new Manager: ");
                    updatedData.set(10, ui.inputString());  
                    break;
                case 10:
                    System.out.print("Enter new Number of Officer Slsot: ");
                    updatedData.set(11, ui.inputString()); 
                    break;
                case 11:
                    System.out.print("Enter new Officers: ");
                    updatedData.set(12, ui.inputString()); 
                    break;
                case 12:
                    System.out.print("Enter new Officer Applying: ");
                    updatedData.set(13, ui.inputString());  
                    break;
                default:
                    System.out.println("Please select a valid category");
                    break;
            }


            storage.updateProject(updatedData);
            System.out.println("Project updated");
        } else {System.out.println("Project not found");}
    }



    
    public void deleteProject(Storage storage) 
    {
        System.out.println("Enter Project Name to delete project: ");
        String projectName = ui.inputString();
        Project project = storage.getProjectByName(projectName);
        if (project != null) 
        {
            storage.removeProject(projectName);
            System.out.println("Project deleted");
        } else {System.out.println("Project not found");}
    }
    

    
    public void viewAllProjects(Storage storage) 
    {
        List<Project> projects = storage.getAllProjects();
        for (Project project : projects) {System.out.println(project);}
    }
    
    /*
     * Able to filter and view the list of projects that they have created only.
     */
    
    public void viewCreatedProject(Storage storage) 
    {
        List<Project> projects = storage.getProjectsByManager(getUserID());
        for (Project project : projects) {System.out.println(project);}
    }
    
    
    public void toggleProjectVisibility(Storage storage) 
    {
    	/*
    	 * not sure if they want toggled as in like a yes/no switch but i thought being able to 
    	 * choose which wld be easier? like so u dont toggle wrong way
    	 */
        System.out.println("Enter Project Name for project which you would like to toggle visibility for: ");
        String projectName = ui.inputString(); 
        Project project = storage.getProjectByName(projectName);

        if (project != null) 
        {
            List<String> updatedData = new ArrayList<>(project.getListOfStrings());
            System.out.print("Enter Visibility (0 for Not visbile, 1 for Visible): ");
            int visibilityInput = ui.inputInt();
            updatedData.set(15, visibilityInput == 1 ? "TRUE" : "FALSE");
            storage.updateProject(updatedData);
            System.out.println("Project visibility changed");
        } 
        else {System.out.println("Project not found");}
    }

    
	
	
	public void viewHDBOfficerRegistration(Storage storage) 
	{
		
		System.out.println("Enter Project Name to view officers registered and applying: ");
		String projectName = ui.inputString();
		Project project = storage.getProjectByName(projectName);
		if (project != null) 
        {
		    ProjectTeam team = project.getProjectTeam();
		    System.out.println("Officers Registered:" + team.getOfficers() + "\" Officers Applying:" + team.getOfficersApplying());

        } else {System.out.println("Project not found");}
	}
	
	public void approveHDBOfficerRegistration(Storage storage) 
	{
	    System.out.println("Enter Project Name for officer registration approval: ");
	    String projectName = ui.inputString();
	    Project project = storage.getProjectByName(projectName);
	    
	    if (project != null && project.getCreatedBy().equals(getUserID())) 
	    {
	        System.out.println("Enter Officer ID to approve: ");
	        String officerID = ui.inputString();
	        ProjectTeam team = project.getProjectTeam();
	        
	        if (team.getOfficersApplying().contains(officerID)) 
	        {
	            team.getOfficers().add(officerID);
	            team.getOfficersApplying().remove(officerID);
	            team.setSlots(team.getSlots() - 1);
	            System.out.println("Officer approved");
	        } else {System.out.println("No pending application");}
	    } else {System.out.println("Project not accessible"); }
	}
	
	public void rejectHDBOfficerRegistration(Storage storage) 
	{
	    System.out.println("Enter Project Name for officer registration rejection: ");
	    String projectName = ui.inputString();
	    Project project = storage.getProjectByName(projectName);
	    
	    if (project != null && project.getCreatedBy().equals(getUserID())) 
	    {
	        System.out.println("Enter Officer ID to reject: ");
	        String officerID = ui.inputString();
	        ProjectTeam team = project.getProjectTeam();
	        
	        if (team.getOfficersApplying().contains(officerID)) 
	        {
	            team.getOfficersApplying().remove(officerID);
	            System.out.println("Officer rejected.");
	        } else {System.out.println("No pending application");}
	    } else {System.out.println("Project not accessible");}
	}
	
	/*
	 * i assumed applicant stuff is only for their own projects
	 */
	
	
	public void approveApplicantApplication(Storage storage) 
	{
	    System.out.println("Enter Project Name for application approval: ");
	    String projectName = ui.inputString();
	    Project project = storage.getProjectByName(projectName);
	    
	    if (project != null && project.getCreatedBy().equals(getUserID())) 
	    {
	        System.out.println("Enter Applicant ID to approve: ");
	        String applicantID = ui.inputString();
	        BTOApplication application = storage.getApplicantApplicationByID(applicantID);
	        FlatType targetType = application.getFlatType(); 
	        HashMap<FlatType, Integer> units = project.getUnits();
	        Integer availableUnits = units.get(targetType);
	        System.out.println("Number of units for " + targetType + ": " + availableUnits);
	        
	        if (application.getApplicationStatus()==ApplicationStatus.PENDING) 
	        {

	        
		        if (availableUnits != 0)
		        {
		            application.setApplicationStatus(ApplicationStatus.SUCCESSFUL);
		            
		            System.out.println("Applicant approved");
		        } else {System.out.println("No units available");}
	        } else {System.out.println("No pending application");}
	    } else {System.out.println("Project not accessible");}
	}
	
	public void rejectApplicantApplication(Storage storage) 
	{
	    System.out.println("Enter Project Name for application rejection: ");
	    String projectName = ui.inputString();
	    Project project = storage.getProjectByName(projectName);
	    
	    if (project != null && project.getCreatedBy().equals(getUserID())) 
	    {
	        System.out.println("Enter Applicant ID to reject: ");
	        String applicantID = ui.inputString();
	        BTOApplication application = storage.getApplicantApplicationByID(applicantID);
	        
	        if (application.getApplicationStatus()==ApplicationStatus.PENDING) 
	        {
	        	application.setApplicationStatus(ApplicationStatus.UNSUCCESSFUL);
	            System.out.println("Applicant rejected");
	        } else {System.out.println("No pending application");}
	    } else {System.out.println("Project not accessible");}
	}
	
	
	/*
	 * I added 2 more to the application status, withdrawn and withdrawal rejected cuz i 
	 * wasnt rly sure if rejecting withdrawal wld lead to successful or which category
	 */
	
	public void approveWithdrawalRequest(Storage storage) 
	{
	    System.out.println("Enter Project Name for withdrawal approval: ");
	    String projectName = ui.inputString();
	    Project project = storage.getProjectByName(projectName);
	    
	    if (project != null && project.getCreatedBy().equals(getUserID())) 
	    {
	        System.out.println("Enter Applicant ID to approve: ");
	        String applicantID = ui.inputString();
	        BTOApplication application = storage.getApplicantApplicationByID(applicantID);
	        
	        if (application != null && application.getApplicationStatus()==ApplicationStatus.WITHDRAWING) 
	        {
	        	application.setApplicationStatus(ApplicationStatus.WITHDRAWN);
	        	System.out.println("Withdrawal approved");
	        } else {System.out.println("No withdrawal application");}
	    } else {System.out.println("Project not accessible");}
	}
	
	public void rejectWithdrawalRequest(Storage storage) 
	{
	    System.out.println("Enter Project Name for withdrawal approval: ");
	    String projectName = ui.inputString();
	    Project project = storage.getProjectByName(projectName);
	    
	    if (project != null && project.getCreatedBy().equals(getUserID())) 
	    {
	        System.out.println("Enter Applicant ID to approve: ");
	        String applicantID = ui.inputString();
	        BTOApplication application = storage.getApplicantApplicationByID(applicantID);
	        
	        if (application != null && application.getApplicationStatus()==ApplicationStatus.WITHDRAWING) 
	        {
	        	application.setApplicationStatus(ApplicationStatus.WITHDRAWAL_REJECTED);
	        	System.out.println("Withdrawal rejected");
	        } else {System.out.println("No withdrawal application");}
	    } else {System.out.println("Project not accessible");}
	}
	
	/*
	 * need to be able to add filters for various categories 
	 */
	public void generateApplicantReport(Storage storage)
	{
	    System.out.println("Enter Project Name to generate report: ");
	    String projectName = ui.inputString();
	    Project project = storage.getProjectByName(projectName);
	    
	    if (project != null) 
	    {
	    	for (BTOApplication app : storage.getBTOApplications().values()) 
	    	{
	    		if(app.getProjectName().equals(projectName)) 
	    		{
	    			String applicantID = app.getApplicantID();
	    			ArrayList<String> userData = storage.getUserData(applicantID);
	    			System.out.println("Application for: " + projectName);
			        System.out.println("Applicant Name     : " + userData.get(0));
			        System.out.println("Applicant ID     : " + userData.get(1));
			        System.out.println("Applicant Age     : " + userData.get(2));
			        System.out.println("Applicant Marital Status     : " + userData.get(3));
			        System.out.println("Flat Type        : " + app.getFlatType());
			        System.out.println("Application Status: " + app.getApplicationStatus());
			        System.out.println("");
	    		}    
			        
		    }
	    } else {System.out.println("Project not found");}
	}
	

	public void viewEnquiries(Storage storage) 
	{
	    System.out.println("Enter Project Name to view enquiries: ");
	    String projectName = ui.inputString();
	    Project project = storage.getProjectByName(projectName);

	    
	    if (project != null) 
	    {
	    	for(Enquiry e : storage.getEnquiries().values()) 
	    	{
	    	    if(e.getProjectName().equals(projectName)) {System.out.println(e);}
	     //       else {System.out.println("No enquiries found");}
	    	    
	        }
	    } else {System.out.println("Project not found");}
	}

	public void replyToEnquiry(Storage storage) 
	{
	    viewEnquiries(storage);
	    System.out.println("Please choose the Enquiries ID: ");
	    String enquiryID = ui.inputString();
	    for(Enquiry e : storage.getEnquiries().values()) 
	    {	
	    	String enquiryProjectName = e.getProjectName();
    	    Project p = storage.getProjectByName(enquiryProjectName);
	    
	    	if (p.getCreatedBy().equals(getUserID()))
    	    {
		        if (e.getID().equals(enquiryID)&&e.getReply().equals("NULL")) 
		        {
		            System.out.println("Please write your reply: ");
		            String reply = ui.inputString();
		            e.setReply(reply);
		            System.out.println("Reply sent");
		        }
		        else {System.out.println("No enquiry found or enquiry has been answered");}
	            
    	    }
    	    //else {System.out.println("You may not reply to this enquiry as it is not your project");}

	    }
	}
}


	
	
	  