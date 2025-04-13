package storage;

import java.util.*;

public class Storage {
    StorageController storageController;
    //NAME, USER_ID, AGE, MARITAL, PASSWORD, TYPE
    private Map<String, ArrayList<String>> USERS; //Main storage for user info, only creating one user at a time
    private Map<String, Project> PROJECTS; //Key: Project Name, Value: Project
    private Map<String, Enquiry> ENQUIRIES; //Key: ID, Value: Enquiry
    private Map<String, BTOApplication> BTOAPPLICATIONS; //Key: UserID, Value: BTOApplication

    public Storage(){
        storageController = new StorageController();
        USERS = storageController.readUserFile();
        ENQUIRIES = storageController.readEnquiryFile();
        PROJECTS = storageController.readProjectFile();
        BTOAPPLICATIONS = storageController.readBTOApplicationFile();
    }

    /**
     * @param userID Users NRIC
     * @return The Users basic details
     */
    public ArrayList<String> getUserData(String userID){
        return USERS.get(userID);
    }

    /**
     * Update basic detail of the User
     * @param userData List of new basic details of User
     */
    public void updateUserData(List<String> userData) { //update with new info
        USERS.replace(userData.get(1), (ArrayList<String>) userData);
    }


    public Map<String, Project> getProject(){;
        return PROJECTS;
    }
    public void updateProject(List<String> projectData) {
    }
    public void addProject(Project newProject) {
        PROJECTS.put(newProject.getProjectName(), newProject);
    }

    public void registerProject(String userID, String projectName) {
        PROJECTS.get(projectName).getProjectTeam().addOfficerApplying(userID);
    }


    public Map<String, Enquiry> getEnquiries(){
        return ENQUIRIES;
    }
    public void updateEnquiries(String ID, Enquiry newEnquiry) {
        ENQUIRIES.put(ID, newEnquiry);
    }
    public void addEnquiries(String askerID, String projectName, String question) {
        ENQUIRIES = storageController.addEnquiry(askerID, projectName, question, ENQUIRIES);
    }
    public void removeEnquiries(String ID) {
        ENQUIRIES.remove(ID);
    }


    public Map<String, BTOApplication> getBTOApplications(){ return BTOAPPLICATIONS; }
    public void addBTOApplication(String userID, String projectName, String price, String type) {
        BTOAPPLICATIONS.put(userID, new BTOApplication(userID, projectName, price, "NULL", type, "PENDING"));
    }

    /**
     * Called when quitting the Application
     */
    public void close(){
        storageController.writeUserFile(USERS);
        storageController.writeProjectFile(PROJECTS);
        storageController.writeEnquiryFile(ENQUIRIES);
        storageController.writeBTOApplicationFile(BTOAPPLICATIONS);
    }
}
