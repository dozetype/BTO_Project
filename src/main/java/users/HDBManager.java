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
    
    /**
     * ui.inputInt adds a "enter Number:" on top of the existing message
     */
    
    /**
     * Create project function, with feature:
     * Can only be handling one project within an application period (from
     * application opening date, inclusive, to application closing date,
     * inclusive)
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
        
        /**
         * checks that entry follows xx/xx/xxxx
         * makes sure the date is a valid date too (so that cant be like 10/30/2020)
         * min/max year can adjust to any number, using 30 years span currently 
         */
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
        sdf.setLenient(false);
        Date openingDate =null;
        Date closingDate =null;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int minYear = currentYear - 30; //
        int maxYear = currentYear + 30;
        List<Project> existingProjects = storage.getProjectsByManager(getUserID());
        boolean validDates = false;
        
        /**
         * ensures loop until valid dates (no overlaps with other managed projects)
         */
        validDatesLoop:
        while (!validDates) 
        {
        
        	while (true) 
        	{
                System.out.print("Enter Application Opening Date (dd/MM/yyyy): ");
                String openingDateStr = ui.inputString();

                /**
                 * obtains opening date, ensuring that it is in valid dd/MM/yyyy format and in somewhat
                 * recent years to minimise typos causing dates which would not be realistic/too far off
                 * from current times
                 */
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

                /**
                 * does the same as for opening date, but with additional check that the closing
                 * date is after the opening date, so that it makes chronological sense
                 */
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
                    else 
                    {
                    	/**
                    	 * check for overlaps in projects
                    	 */
                        boolean overlapFound = false;
                        for (Project project : existingProjects) 
                        {
                            Long existingOpen = project.getOpeningDate();
                            Long existingClose = project.getClosingDate();
                            if ((openingDate.getTime() < existingClose) && (closingDate.getTime() > existingOpen)) 
                            {
                                System.out.println("The project overlaps with an existing project. Please reenter the dates.");
                                overlapFound = true;
                                break;
                            }
                        }

                        /**
                         * if overlap go back to re-enter opening date again
                         * no overlap, then break loop
                         */
                        if (overlapFound) 
                        {
                            continue validDatesLoop; 
                        } else 
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


    /**
     * function to show list of projects, so that it is easier for manager to select
     * which project they would like to perform an action for, by choosing a number, rather
     * than having to type out a long name
     */
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
    
    /**
     * same function as above, but only for the projects the manager is managing/has created
     */
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
    
    /**
     * function to edit the different fields of the project, other than visibility, which has its
     * own "toggle visibility" function. did not allow edits for certain fields, such as neighborhood
     * (would be difficult or almost impossible to change location of a current project), project name
     * (unique identifier which would likely not be edited due to causing much confusion), types of 
     * flats (fixed to only 2 types in this scenario), manager(automatically tied to manager who
     * created the project), officers, officers applying and officers rejected (all 3 will be modified  
     * through different methods of accepting, rejecting officers applying)
     */
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



    /**
     * function to delete project
     */
    
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


    /**
     * function to view all created projects, including projects created by other
     * HDB Managers, regardless of visibility setting.
     */
    public void viewAllProjects(IStorage storage)
    {
        List<Project> projects = storage.getAllProjects();
        for (Project p : projects) 
        {
        	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        	String openDate = sdf.format(new Date(p.getOpeningDate()));
        	String closeDate = sdf.format(new Date(p.getClosingDate()));
        
    			System.out.println("Project Name       : " + p.getProjectName());
    		    System.out.println("Neighbourhood      : " + p.getNeighbourhood());
    		    System.out.println("Opening Date       : " + openDate);
    		    System.out.println("Closing Date       : " + closeDate);
    		    System.out.println("Manager            : " + p.getCreatedBy());
    		    System.out.println("Visibility         : " + (p.getProjectVisibility() ? "Visible" : "Not Visible"));
    		    /**
    		     * get the units and number available
    		     */
    		    StringBuilder unitStr = new StringBuilder();
    		    for (Map.Entry<FlatType, Integer> entry : p.getUnits().entrySet()) {
    		        unitStr.append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
    		    }
    		    if (!unitStr.isEmpty()) unitStr.setLength(unitStr.length() - 2); // remove trailing comma
    		    System.out.println("Unit Breakdown     : " + unitStr);
    		    /**
    		     * get the prices for each unit type 
    		     */
    		    StringBuilder priceStr = new StringBuilder();
    		    for (Map.Entry<FlatType, Integer> entry : p.getPrices().entrySet()) {
    		        priceStr.append(entry.getKey()).append("=$").append(String.format("%,d", entry.getValue())).append(", ");
    		    }
    		    if (!priceStr.isEmpty()) priceStr.setLength(priceStr.length() - 2);
    		    System.out.println("Pricing            : " + priceStr);

    		    List<String> teamDetails = p.getProjectTeam().getListOfStrings();
    		    System.out.println("Max Officer Slots  : " + teamDetails.get(1));

    		    /**
    		     *  officers on project, appplying and rejected
    		     */
    		    String accepted = teamDetails.get(2).replace("\"", "").trim();
    		    System.out.println("Approved Officers  :");
    		    if (!accepted.isEmpty()) {
    		        for (String id : accepted.split("\\.")) {
    		            System.out.println(" - " + id);
    		        }
    		    } else {
    		        System.out.println("None");
    		    }

    		    String applying = teamDetails.get(3).replace("\"", "").trim();
    		    System.out.println("Pending Applications  :");
    		    if (!applying.isEmpty()) {
    		        for (String id : applying.split("\\.")) {
    		            System.out.println(" - " + id);
    		        }
    		    } else {
    		        System.out.println("None");
    		    }

    		    String rejected = teamDetails.get(4).replace("\"", "").trim();
    		    System.out.println("Rejected Applications :");
    		    if (!rejected.isEmpty()) {
    		        for (String id : rejected.split("\\.")) {
    		            System.out.println(" - " + id);
    		        }
    		    } else {
    		        System.out.println("None");
    		    }
    		    
		        System.out.println("");
                
		        
	    }
    }
    
    
    /**
     * Able to filter and view the list of projects that they have created only. uses same structure 
     * as viewing all functions, but ensures created by is the user ID
     */
    
    public void viewCreatedProject(IStorage storage)
    {
        List<Project> projects = storage.getProjectsByManager(getUserID());
        for (Project p : projects) 
        {
        	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        	String openDate = sdf.format(new Date(p.getOpeningDate()));
        	String closeDate = sdf.format(new Date(p.getClosingDate()));
        
    			System.out.println("Project Name       : " + p.getProjectName());
    		    System.out.println("Neighbourhood      : " + p.getNeighbourhood());
    		    System.out.println("Opening Date       : " + openDate);
    		    System.out.println("Closing Date       : " + closeDate);
    		    System.out.println("Manager            : " + p.getCreatedBy());
    		    System.out.println("Visibility         : " + (p.getProjectVisibility() ? "Visible" : "Not Visible"));
    		    //get the units and number available
    		    StringBuilder unitStr = new StringBuilder();
    		    for (Map.Entry<FlatType, Integer> entry : p.getUnits().entrySet()) {
    		        unitStr.append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
    		    }
    		    if (!unitStr.isEmpty()) unitStr.setLength(unitStr.length() - 2); // remove trailing comma
    		    System.out.println("Unit Breakdown     : " + unitStr);
    		    //get the prices for each unit type 
    		    StringBuilder priceStr = new StringBuilder();
    		    for (Map.Entry<FlatType, Integer> entry : p.getPrices().entrySet()) {
    		        priceStr.append(entry.getKey()).append("=$").append(String.format("%,d", entry.getValue())).append(", ");
    		    }
    		    if (!priceStr.isEmpty()) priceStr.setLength(priceStr.length() - 2);
    		    System.out.println("Pricing            : " + priceStr);

    		    List<String> teamDetails = p.getProjectTeam().getListOfStrings();
    		    System.out.println("Max Officer Slots  : " + teamDetails.get(1));

    		    // officers on project
    		    String accepted = teamDetails.get(2).replace("\"", "").trim();
    		    System.out.println("Approved Officers  :");
    		    if (!accepted.isEmpty()) {
    		        for (String id : accepted.split("\\.")) {
    		            System.out.println(" - " + id);
    		        }
    		    } else {
    		        System.out.println("None");
    		    }

    		    // officers applying
    		    String applying = teamDetails.get(3).replace("\"", "").trim();
    		    System.out.println("Pending Applications  :");
    		    if (!applying.isEmpty()) {
    		        for (String id : applying.split("\\.")) {
    		            System.out.println(" - " + id);
    		        }
    		    } else {
    		        System.out.println("None");
    		    }

    		    // officers rejected
    		    String rejected = teamDetails.get(4).replace("\"", "").trim();
    		    System.out.println("Rejected Applications :");
    		    if (!rejected.isEmpty()) {
    		        for (String id : rejected.split("\\.")) {
    		            System.out.println(" - " + id);
    		        }
    		    } else {
    		        System.out.println("None");
    		    }
    		    
		        System.out.println("");
        }
    }
    
    
    /**
	 * To toggle the visibility of the project to “on” or “off”.
	 * Instead of toggling, for user convenience the manager can select either 0 for not visible
	 * or 1 for visible, to ensure that they are selecting the visibility they would like to opt for, 
	 * reducing mistakes in toggling to an incorrect visibility state
	 */
    
    public void toggleProjectVisibility(IStorage storage)
    {
    	
    	
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
			System.out.println("Project visibility set");
		} else {System.out.println("Project not found");}
		
    }

    
    /**
     * View pending and approved HDB officer registration
     */
	
	
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
                    System.out.println("Viewing cancelled.");
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

	
	/**
	 * Combined function to to approve or reject HDB Officer’s registration as the HDB Manager 
	 * in-charge of the project and update project’s remaining HDB Officer slots
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
                    System.out.println("Decision cancelled.");
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
	

	
	/**
	 * Approving applicant's applications, but using the assumption that they are only able to
	 * access the applications for their own managed projects
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
                    System.out.println("Approval cancelled.");
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
	
	/**
	 * Rejecting applicant's applications, but using the assumption that they are only able to
	 * access the applications for their own managed projects
	 */
	
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
                    System.out.println("Rejection cancelled.");
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
	
	
	/**
	 * Approving withdrawal request: application is set to unsuccessful as long as there is a valid
	 * application
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
                    System.out.println("Approval cancelled.");
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
	        	application.setApplicationStatus(ApplicationStatus.UNSUCCESSFUL);
	        	System.out.println("Withdrawal approved");
	        } else {System.out.println("No withdrawal application");}
	    } else {System.out.println("Project not accessible");}
	}
	

    /**
     * Rejecting withdrawal request: Application status changed to withdrawal rejected
     * if withdrawal is rejected
     * 
     */
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
                    System.out.println("Rejection cancelled.");
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
	
	/**
	 * Generating an applicant report; a list of applicants with their respective applications, 
	 * with flat type, age, marital status and filtering based on applicant's choice of flat type 
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
                    System.out.println("Generation cancelled.");
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
	

	/**
	 * viewing enquiries for all projects
	 */
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
                    System.out.println("Viewing cancelled.");
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

	/**
	 * Replying to enquiries but only for their own projects
	 */
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