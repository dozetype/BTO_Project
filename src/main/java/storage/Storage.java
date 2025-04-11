package storage;

import java.util.*;

public class Storage {
    StorageController storageController;
    //NAME, USER_ID, AGE, MARITAL, PASSWORD, TYPE
    private Map<String, ArrayList<String>> USERS; //Main storage for user info, only creating one user at a time
    private Map<String, Project> PROJECTS;
    private Map<String, Enquiry> ENQUIRIES;
    private Map<String, BTOApplication> BTOAPPLICATIONS;

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


    public List<Project> getProject(){
        List<Project> projects = new ArrayList<>();
        for(Project p : PROJECTS.values()){
            if(p.getProjectVisibility())
                projects.add(p);
        }
        return projects;
    }
    public void updateProject(List<String> projectData) {
    }
    public void addProject(Project newProject) {
        PROJECTS.put(newProject.getProjectName(), newProject);
    }

    //TODO Maybe register detail show in another project.csv
//    public void registerProject(String userID, String projectName) {
//        List<String> OfficerList = new ArrayList<>();
//        if (PROJECTS.containsKey(projectName)) {
//            Project projectInfo = PROJECTS.get(projectName);
//            projectInfo.add(userID);
//        }
//    }


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
