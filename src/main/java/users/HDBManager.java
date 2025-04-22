package users;

import storage.*;
import ui.Ui;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class HDBManager extends User 
{
    Ui ui = new Ui();
//    private Storage storage;
    
    public HDBManager(List<String> userData) 
    {
        super(userData, "Manager");
    }
    
    /*
     * ui.inputInt adds a "enter Number:" on top of the existing message
     */
    /*
     * Can only be handling one project within an application period (from
application opening date, inclusive, to application closing date,
inclusive)
     */
    public void createProject(IStorage storage)
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
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); //checks that entry follows xx/xx/xxxx
        sdf.setLenient(false); //makes sure the date is a valid date too (so that cant be like 10/30/2020)
        Date openingDate =null;
        Date closingDate =null;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int minYear = currentYear - 40; //can adjust to any number 
        int maxYear = currentYear + 40;
        List<Project> existingProjects = storage.getProjectsByManager(getUserID());
        boolean validDates = false;

        while (!validDates) 
        {
        
        	while (true) 
        	{
                System.out.print("Enter Application Opening Date (dd/MM/yyyy): ");
                String openingDateStr = ui.inputString();

                try 
                {
                	openingDate = sdf.parse(openingDateStr);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(openingDate);
                    int openYear = cal.get(Calendar.YEAR);

                    if (openYear < minYear || openYear > maxYear) 
                    {System.out.println("Opening year must be between " + minYear + " and " + maxYear + ".");} 
                    else {break;}
                } catch (ParseException e) {System.out.println("Invalid date format. Please enter in dd/MM/yyyy format.");}
            }

            while (true) 
            {
                System.out.print("Enter Application Closing Date (dd/MM/yyyy): ");
                String closingDateStr = ui.inputString();

                try 
                {
                	closingDate = sdf.parse(closingDateStr);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(closingDate);
                    int closeYear = cal.get(Calendar.YEAR);

                    if (closeYear < minYear || closeYear > maxYear) 
                    {System.out.println("Closing date must be between " + minYear + " and " + maxYear + "."); } 
                    else if (!closingDate.after(openingDate)) 
                    {System.out.println("Closing date must be after the opening date.");} 
                    else {
                        boolean overlapFound = false;
                        for (Project project : existingProjects) 
                        {
                            Long existingOpen = project.getOpeningDate();
                            Long existingClose = project.getClosingDate();
                            if ((openingDate.getTime() < existingClose) && (closingDate.getTime() > existingOpen)) {
                                System.out.println("The project overlaps with an existing project. Please reenter the dates.");
                                overlapFound = true;
                                break;
                            }
                        }

                        if (!overlapFound) 
                        {
                            validDates = true;
                            break;
                        }
                    }

                } catch (ParseException e) {System.out.println("Invalid date format. Please enter in dd/MM/yyyy format.");}
            }
        }
        
        int visibilityInput;
        while (true) 
        {
            System.out.print("Enter Visibility (0 for Not visible, 1 for Visible): ");
            visibilityInput = ui.inputInt();

            if (visibilityInput == 0 || visibilityInput == 1) {break;} 
            else {System.out.println("Please enter 0 or 1");}
        }
        boolean visibility = (visibilityInput == 1);
        
        int officerSlots;
        while (true) 
        {
            System.out.print("Enter Maximum number of Officer Slots: ");
            officerSlots = ui.inputInt();

            if (officerSlots <11) {break;} 
            else {System.out.println("Maximum number of officers is 10");}
        }
        

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
            sdf.format(openingDate), 
            sdf.format(closingDate), 
            getUserID(), 
            String.valueOf(officerSlots), 
            "", 
            "",
            "", 
            String.valueOf(visibility)
        };
        
        Project projectAdding = new Project(project);
        storage.addProject(projectAdding);
        System.out.println("Project " + projectName + " created");
    }


    public List<String> showProjectslist(IStorage storage){
        List<String> projectNames = new ArrayList<>();
        int count = 1;

        for (Project p : storage.getProject().values()) {
            projectNames.add(p.getProjectName());
            System.out.println(count++ + ") " + p.getProjectName());
        }

        if (projectNames.isEmpty())
        {
            System.out.println("No projects found.");
            return projectNames;
        }
        return projectNames;
    }
    
    public List<String> showManagedProjectslist(IStorage storage)
    {
        List<String> projectNames = new ArrayList<>();
        int count = 1;
        String currentUserID = getUserID();
        
        for (Project p : storage.getProject().values()) 
        {
            String projectManagerID = p.getCreatedBy(); 

            if (currentUserID.equalsIgnoreCase(projectManagerID)) {
                projectNames.add(p.getProjectName());
                System.out.println(count++ + ") " + p.getProjectName());
            }
        }
        if (projectNames.isEmpty()) {
            System.out.println("No Projects Found");
        }
        
        return projectNames;
    }
    
    public void editProject(IStorage storage)
    {
    	List<String> projectNames = showProjectslist(storage);
    	if (projectNames.isEmpty()) {return;}
    	int choice = -1;
        while (true) 
        {
            System.out.println("Enter the number of the project to edit or 0 to cancel: ");

            String input = ui.inputString();
            try 
            {
                choice = Integer.parseInt(input); 

                if (choice == 0) 
                {
                    System.out.println("Edit cancelled.");
                    return;
                }

                if (choice >= 1 && choice <= projectNames.size()) {break;}
                else {System.out.println("Invalid number. Please enter a number between 1 and " + projectNames.size() + ", or 0 to cancel.");}
            } catch (NumberFormatException e) {System.out.println("Invalid input. Please enter a valid number.");}
        }

        String projectName = projectNames.get(choice - 1);
        Project project = storage.getProjectByName(projectName);
        
        if (project != null)
        {
            System.out.println("Editing Project: " + project.getProjectName());
            System.out.println("Which category would you like to edit?");
            System.out.println("1) Number of Units for Type 1");
            System.out.println("2) Selling Price for Type 1");
            System.out.println("3) Number of Units for Type 2");
            System.out.println("4) Selling Price for Type 2");
            System.out.println("5) Application Opening Date");
            System.out.println("6) Application Closing Date");
            System.out.println("7) Number of Officer Slots");
            System.out.println("Note: Select Toggle Visibility to Change Visibility");

            int c = ui.inputInt();
            List<String> updatedData = new ArrayList<>(project.getListOfStrings());

            switch (c) 
            {
                case 1:
                    System.out.print("Enter new Number of Units for Type 1: ");
                    updatedData.set(3, ui.inputString());  
                    break;
                case 2:
                    System.out.print("Enter new Selling Price for Type 1: ");
                    updatedData.set(4, ui.inputString());  
                    break;
                case 3:
                    System.out.print("Enter new Number of Units for Type 2: ");
                    updatedData.set(6, ui.inputString());  
                    break;
                case 4:
                    System.out.print("Enter new Selling Price for Type 2: ");
                    updatedData.set(7, ui.inputString());  
                    break;
                case 5:
                    SimpleDateFormat sdfo = new SimpleDateFormat("dd/MM/yyyy"); //checks that entry follows xx/xx/xxxx
                    sdfo.setLenient(false); //makes sure the date is a valid date too (so that cant be like 10/30/2020)
                    while (true) 
                    {
                        System.out.print("Enter Application Opening Date (dd/MM/yyyy): ");
                        String inputDate = ui.inputString();

                        try {
                            Date opencheck = sdfo.parse(inputDate);  
                            Long openingDate = opencheck.getTime();
                            updatedData.set(8, String.valueOf(openingDate));  
                            break;  
                        } catch (ParseException e) {
                            System.out.println("Invalid date format. Please enter in dd/MM/yyyy format.");
                        }
                    }
                    break;
                case 6:
                    SimpleDateFormat sdfc = new SimpleDateFormat("dd/MM/yyyy"); //checks that entry follows xx/xx/xxxx
                    sdfc.setLenient(false); //makes sure the date is a valid date too (so that cant be like 10/30/2020)

                    while (true) 
                    {
                        System.out.print("Enter Application Closing Date (dd/MM/yyyy): ");
                        String inputDate = ui.inputString();  

                        try {
                            Date closecheck = sdfc.parse(inputDate);  
                            Long closingDate = closecheck.getTime();
                            updatedData.set(9, String.valueOf(closingDate));  
                            break;  
                        } catch (ParseException e) {
                            System.out.println("Invalid date format. Please enter in dd/MM/yyyy format.");
                        }
                    }
                    
                    break;
                case 7:
                    System.out.print("Enter new Number of Officer Slots: ");
                    updatedData.set(11, ui.inputString()); 
                    break;
                default:
                    System.out.println("Please select a valid category");
                    break;
            }


            storage.updateProject(updatedData);
            System.out.println("Project updated");
        } else {System.out.println("Project not found");}
    }



    
    public void deleteProject(IStorage storage)
    {
    	List<String> projectNames = showProjectslist(storage);
    	if (projectNames.isEmpty()) {return;}
    
        int choice = -1;
        while (true) 
        {
            System.out.println("Enter the number of the project to delete or 0 to cancel: ");

            String input = ui.inputString();
            try 
            {
                choice = Integer.parseInt(input); 

                if (choice == 0) 
                {
                    System.out.println("Deletion cancelled.");
                    return;
                }

                if (choice >= 1 && choice <= projectNames.size()) {break;}
                else {System.out.println("Invalid number. Please enter a number between 1 and " + projectNames.size() + ", or 0 to cancel.");}
            } catch (NumberFormatException e) {System.out.println("Invalid input. Please enter a valid number.");}
        }

        String projectName = projectNames.get(choice - 1);
        Project project = storage.getProjectByName(projectName);
        if (project != null) 
        {
            storage.removeProject(projectName);
            System.out.println("Project deleted.");
        } else {System.out.println("Project not found.");}
    }


    
    public void viewAllProjects(IStorage storage)
    {
        List<Project> projects = storage.getAllProjects();
        for (Project project : projects) {System.out.println(project);}
    }
    
    
    /*
     * Able to filter and view the list of projects that they have created only.
     */
    
    public void viewCreatedProject(IStorage storage)
    {
        List<Project> projects = storage.getProjectsByManager(getUserID());
        for (Project project : projects) {System.out.println(project);}
    }
    
    
    public void toggleProjectVisibility(IStorage storage)
    {
    	/*
    	 * not sure if they want toggled as in like a yes/no switch but i thought being able to 
    	 * choose which wld be easier? like so u dont toggle wrong way
    	 */
    	
    	List<String> projectNames = showProjectslist(storage);
    	if (projectNames.isEmpty()) {return;}
    	int choice = -1;
        while (true) 
        {
            System.out.println("Enter the number of the project to toggle visibility or 0 to cancel: ");

            String input = ui.inputString();
            try 
            {
                choice = Integer.parseInt(input); 

                if (choice == 0) 
                {
                    System.out.println("Toggling cancelled.");
                    return;
                }

                if (choice >= 1 && choice <= projectNames.size()) {break;}
                else {System.out.println("Invalid number. Please enter a number between 1 and " + projectNames.size() + ", or 0 to cancel.");}
            } catch (NumberFormatException e) {System.out.println("Invalid input. Please enter a valid number.");}
        }

        String projectName = projectNames.get(choice - 1);
        Project project = storage.getProjectByName(projectName);
        if (project != null) 
        {
			List<String> updatedData = new ArrayList<>(project.getListOfStrings());
			System.out.print("Enter Visibility (0 for Not visible, 1 for Visible): ");
			int visibilityInput = ui.inputInt();
			updatedData.set(15, visibilityInput == 1 ? "TRUE" : "FALSE");
			storage.updateProject(updatedData);
			System.out.println("Project visibility changed");
		} else {System.out.println("Project not found");}
		
    }

    
	
	
	public void viewHDBOfficerRegistration(IStorage storage)
	{
		
		List<String> projectNames = showProjectslist(storage);
		if (projectNames.isEmpty()) {return;}
		int choice = -1;
        while (true) 
        {
            System.out.println("Enter the number of the project to view officer registration or 0 to cancel: ");

            String input = ui.inputString();
            try 
            {
                choice = Integer.parseInt(input); 

                if (choice == 0) 
                {
                    System.out.println("Deletion cancelled.");
                    return;
                }

                if (choice >= 1 && choice <= projectNames.size()) {break;}
                else {System.out.println("Invalid number. Please enter a number between 1 and " + projectNames.size() + ", or 0 to cancel.");}
            } catch (NumberFormatException e) {System.out.println("Invalid input. Please enter a valid number.");}
        }

        String projectName = projectNames.get(choice - 1);
        Project project = storage.getProjectByName(projectName);
		if (project != null) 
        {
		    ProjectTeam team = project.getProjectTeam();
		    System.out.println("Officers Registered:" + team.getOfficers() + "\" Officers Applying:" + team.getOfficersApplying());

        } else {System.out.println("Project not found");}
	}
	/*
	public void approveHDBOfficerRegistration(Storage storage) 
	{
		List<String> projectNames = showManagedProjectslist(storage);
        System.out.println("Enter the number of the project to approve officer registration: ");
        String projectName = projectNames.get(ui.inputInt() - 1);
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
	            System.out.println("Officer approved");
	        } else {System.out.println("No pending application");}
	    } else {System.out.println("Project not accessible"); }
	}
	*/
	public void decideOfficerApplication(IStorage storage)
	{
		List<String> projectNames = showManagedProjectslist(storage);
		if (projectNames.isEmpty()) {return;}
		int choice = -1;
        while (true) 
        {
            System.out.println("Enter the number of the project to decide officer application or 0 to cancel: ");

            String input = ui.inputString();
            try 
            {
                choice = Integer.parseInt(input); 

                if (choice == 0) 
                {
                    System.out.println("Deletion cancelled.");
                    return;
                }

                if (choice >= 1 && choice <= projectNames.size()) {break;}
                else {System.out.println("Invalid number. Please enter a number between 1 and " + projectNames.size() + ", or 0 to cancel.");}
            } catch (NumberFormatException e) {System.out.println("Invalid input. Please enter a valid number.");}
        }

        String projectName = projectNames.get(choice - 1);
	    if (projectName == null) return;
	    Project project = storage.getProjectByName(projectName);
	    if (project == null || !project.getCreatedBy().equals(getUserID())) {
	        System.out.println("Project not accessible or you’re not the manager.");
	        return;
	    }
	    project.getProjectTeam().decideOfficerApplication();
	}
	
	/*
	public void rejectHDBOfficerRegistration(Storage storage) 
	{
		List<String> projectNames = showManagedProjectslist(storage);
        System.out.println("Enter the number of the project to reject officer registration: ");
        String projectName = projectNames.get(ui.inputInt() - 1);
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
	}*/
	
	/*
	 * i assumed applicant stuff is only for their own projects
	 */
	
	
	public void approveApplicantApplication(IStorage storage)
	{
		List<String> projectNames = showManagedProjectslist(storage);
		if (projectNames.isEmpty()) {return;}
		int choice = -1;
        while (true) 
        {
            System.out.println("Enter the number of the project to approve applicant's application or 0 to cancel: ");

            String input = ui.inputString();
            try 
            {
                choice = Integer.parseInt(input); 

                if (choice == 0) 
                {
                    System.out.println("Deletion cancelled.");
                    return;
                }

                if (choice >= 1 && choice <= projectNames.size()) {break;}
                else {System.out.println("Invalid number. Please enter a number between 1 and " + projectNames.size() + ", or 0 to cancel.");}
            } catch (NumberFormatException e) {System.out.println("Invalid input. Please enter a valid number.");}
        }

        String projectName = projectNames.get(choice - 1);
        Project project = storage.getProjectByName(projectName);
	    
	    if (project != null && project.getCreatedBy().equals(getUserID())) 
	    {
	        System.out.println("Enter Applicant ID to approve: ");
	        String applicantID = ui.inputString();
	        BTOApplication application = storage.getApplicantApplicationByID(applicantID);
	        if (application == null) {
	            System.out.println("No application found for Applicant ID: " + applicantID);
	            return; 
	        }
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
	
	public void rejectApplicantApplication(IStorage storage)
	{
		List<String> projectNames = showManagedProjectslist(storage);
		if (projectNames.isEmpty()) {return;}
		int choice = -1;
        while (true) 
        {
            System.out.println("Enter the number of the project to reject applicant's application or 0 to cancel: ");

            String input = ui.inputString();
            try 
            {
                choice = Integer.parseInt(input); 

                if (choice == 0) 
                {
                    System.out.println("Deletion cancelled.");
                    return;
                }

                if (choice >= 1 && choice <= projectNames.size()) {break;}
                else {System.out.println("Invalid number. Please enter a number between 1 and " + projectNames.size() + ", or 0 to cancel.");}
            } catch (NumberFormatException e) {System.out.println("Invalid input. Please enter a valid number.");}
        }

        String projectName = projectNames.get(choice - 1);
        Project project = storage.getProjectByName(projectName);
	    
	    if (project != null && project.getCreatedBy().equals(getUserID())) 
	    {
	        System.out.println("Enter Applicant ID to reject: ");
	        String applicantID = ui.inputString();
	        BTOApplication application = storage.getApplicantApplicationByID(applicantID);
	        if (application == null) {
	            System.out.println("No application found for Applicant ID: " + applicantID);
	            return; 
	        }
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
	
	public void approveWithdrawalRequest(IStorage storage)
	{
		List<String> projectNames = showManagedProjectslist(storage);
		if (projectNames.isEmpty()) {return;}
		int choice = -1;
        while (true) 
        {
            System.out.println("Enter the number of the project to approve applicant's withdrawal request or 0 to cancel: ");

            String input = ui.inputString();
            try 
            {
                choice = Integer.parseInt(input); 

                if (choice == 0) 
                {
                    System.out.println("Deletion cancelled.");
                    return;
                }

                if (choice >= 1 && choice <= projectNames.size()) {break;}
                else {System.out.println("Invalid number. Please enter a number between 1 and " + projectNames.size() + ", or 0 to cancel.");}
            } catch (NumberFormatException e) {System.out.println("Invalid input. Please enter a valid number.");}
        }

        String projectName = projectNames.get(choice - 1);
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
	
	public void rejectWithdrawalRequest(IStorage storage)
	{
		List<String> projectNames = showManagedProjectslist(storage);
		if (projectNames.isEmpty()) {return;}
		int choice = -1;
        while (true) 
        {
            System.out.println("Enter the number of the project to reject applicant's withdrawal request or 0 to cancel: ");

            String input = ui.inputString();
            try 
            {
                choice = Integer.parseInt(input); 

                if (choice == 0) 
                {
                    System.out.println("Deletion cancelled.");
                    return;
                }

                if (choice >= 1 && choice <= projectNames.size()) {break;}
                else {System.out.println("Invalid number. Please enter a number between 1 and " + projectNames.size() + ", or 0 to cancel.");}
            } catch (NumberFormatException e) {System.out.println("Invalid input. Please enter a valid number.");}
        }

        String projectName = projectNames.get(choice - 1);
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
	public void generateApplicantReport(IStorage storage)
	{
		List<String> projectNames = showManagedProjectslist(storage);
		if (projectNames.isEmpty()) {return;}
		int choice = -1;
        while (true) 
        {
            System.out.println("Enter the number of the project to generate applicant report or 0 to cancel: ");

            String input = ui.inputString();
            try 
            {
                choice = Integer.parseInt(input); 

                if (choice == 0) 
                {
                    System.out.println("Deletion cancelled.");
                    return;
                }

                if (choice >= 1 && choice <= projectNames.size()) {break;}
                else {System.out.println("Invalid number. Please enter a number between 1 and " + projectNames.size() + ", or 0 to cancel.");}
            } catch (NumberFormatException e) {System.out.println("Invalid input. Please enter a valid number.");}
        }

        String projectName = projectNames.get(choice - 1);
        Project project = storage.getProjectByName(projectName);
	    
	    if (project != null) 
	    {
	    	String flatTypeFilter = getFilter("FLAT_TYPE");
//			storage.getBTOApplications().values().stream().filter(a -> a.getFlatType().toString().equals(flatTypeFilter));
	    	for (BTOApplication app : storage.getBTOApplications().values()) 
	    	{
	    		if(app.getProjectName().equals(projectName)) 
	    		{
	    			
	    			boolean isValid = true;
	                if (flatTypeFilter != null && !app.getFlatType().toString().equals(flatTypeFilter)) {isValid = false;}

	                if (isValid) 
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
			        
		    }
	    } else {System.out.println("Project not found");}
	}
	

	public void viewEnquiries(IStorage storage)
	{
		List<String> projectNames = showProjectslist(storage);
		
		int choice = -1;
        while (true) 
        {
            System.out.println("Enter the number of the project to view enquiries or 0 to cancel: ");

            String input = ui.inputString();
            try 
            {
                choice = Integer.parseInt(input); 

                if (choice == 0) 
                {
                    System.out.println("Deletion cancelled.");
                    return;
                }

                if (choice >= 1 && choice <= projectNames.size()) {break;}
                else {System.out.println("Invalid number. Please enter a number between 1 and " + projectNames.size() + ", or 0 to cancel.");}
            } catch (NumberFormatException e) {System.out.println("Invalid input. Please enter a valid number.");}
        }

        String projectName = projectNames.get(choice - 1);
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

	public void replyToEnquiry(IStorage storage)
	{
		List<String> projectNames = showManagedProjectslist(storage);
		
		System.out.println("Enter the number of the project to view enquiries: ");
		//catches all Index out of bound error
		try {
			String projectName = projectNames.get(ui.inputInt() - 1);
			Map<String, Enquiry> availableEnquiries = new HashMap<>(); //Used to store unanswered relavant
			for (Enquiry e : storage.getEnquiries().values()) {
				if (e.getReply().equals("NULL") && e.getProjectName().equals(projectName)) {
					availableEnquiries.put(e.getID(), e);
					System.out.println(e);
				}
			}
			if (!availableEnquiries.isEmpty()) {
				System.out.println("Please choose the Enquiries ID: ");
				Enquiry toBeReplied = availableEnquiries.get(ui.inputString());
				if (toBeReplied != null) {
					System.out.println("Please write your reply: ");
					toBeReplied.setReply(ui.inputString());
					System.out.println("Thank you for your reply!");
				} else {
					System.out.println("Entered ID does not exist. Exiting.");
				}
			}
		}catch (IndexOutOfBoundsException e) {
			System.out.println("Entered ID does not exist. Exiting.");
		}
	}
}