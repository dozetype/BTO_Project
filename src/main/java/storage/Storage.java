
package storage;

import java.util.*;

public class Storage implements IStorage {
    StorageController storageController;
    //NAME, USER_ID, AGE, MARITAL, PASSWORD, TYPE
    private Map<String, ArrayList<String>> USERS; //Main storage for user info, only creating one user at a time
    private Map<String, Project> PROJECTS; //Key: Project Name, Value: Project
    private Map<String, Enquiry> ENQUIRIES; //Key: ID, Value: Enquiry
    private Map<String, BTOApplication> BTOAPPLICATIONS; //Key: ID, Value: BTOApplication

    public Storage(){
        storageController = new StorageController();
        USERS = storageController.readUserFile();
        ENQUIRIES = storageController.readEnquiryFile();
        PROJECTS = storageController.readProjectFile();
        BTOAPPLICATIONS = storageController.readBTOApplicationFile();
    }

    //Accessors
    public ArrayList<String> getUserData(String userID){
        return USERS.get(userID);
    }
    public Map<String, Project> getProject(){ return PROJECTS; }
    public Project getProjectByName(String projectName)
    {
        for (Project p : PROJECTS.values())
        {
            if (p.getProjectName().equalsIgnoreCase(projectName))
            {return p;}
        }
        return null;
    }
    public List<Project> getAllProjects() {return new ArrayList<>(PROJECTS.values());}
    public List<Project> getProjectsByManager(String managerID)
    {
        List<Project> result = new ArrayList<>();
        for (Project project : PROJECTS.values())
        {
            List<String> data = project.getListOfStrings();
            if (data.get(10).equalsIgnoreCase(managerID)) {result.add(project);}
        }
        return result;
    }
    public Map<String, Enquiry> getEnquiries(){
        return ENQUIRIES;
    }
    public Map<String, BTOApplication> getBTOApplications(){ return BTOAPPLICATIONS; }

    public BTOApplication getApplicantApplicationByID(String userID) { //TODO this only returns 1 BTO Application made by that user
        for (BTOApplication app : getBTOApplications().values()) {
            if (app.getApplicantID().equalsIgnoreCase(userID)) {return app;}
        }
        return null;
    }


    //Mutators
    public void updateUserData(List<String> userData) { //update with new info
        USERS.replace(userData.get(1), (ArrayList<String>) userData);
    }
    public void updateProject(List<String> projectData)
    {
    	String originalProjectName = projectData.get(0);
        Project updatedProject = new Project(projectData.toArray(new String[0]));
        PROJECTS.put(originalProjectName, updatedProject);
    }
    public void addProject(Project newProject) { PROJECTS.put(newProject.getProjectName(), newProject); }
    public void removeProject(String projectName) {PROJECTS.remove(projectName);}
    public void registerProject(String userID, String projectName) {
        PROJECTS.get(projectName).getProjectTeam().addOfficerApplying(userID);
    }
    public void updateEnquiries(String ID, Enquiry newEnquiry) {
        ENQUIRIES.put(ID, newEnquiry);
    }
    public void addEnquiries(String askerID, String projectName, String question) { storageController.addEnquiry(askerID, projectName, question, ENQUIRIES); }
    public void removeEnquiries(String ID) {
        ENQUIRIES.remove(ID);
    }
    public void addBTOApplication(String userID, String projectName, String price, String type) {
        storageController.addBTOApplication(userID, projectName, price, type, BTOAPPLICATIONS);
    }
    public List<Project> getFilteredProjects(Map<String, String> filters) {
        List<Project> copy = new ArrayList<>(PROJECTS.values());
        for(Map.Entry<String, String> entry : filters.entrySet()){
            if(entry.getKey().equals("NEIGHBOURHOOD")){
                copy = copy.stream().filter(project -> project.getNeighbourhood().equalsIgnoreCase(entry.getValue())).toList();
            }
            //can add more filters (besides Flat Type)
        }
        return copy;
    }


    public void close(){
        storageController.writeUserFile(USERS);
        storageController.writeProjectFile(PROJECTS);
        storageController.writeEnquiryFile(ENQUIRIES);
        storageController.writeBTOApplicationFile(BTOAPPLICATIONS);
    }
}
