package storage;

import java.lang.reflect.Array;
import java.util.*;

import static java.util.Arrays.*;

public class Storage {
    StorageController storageController;
    //NAME, USER_ID, AGE, MARITAL, PASSWORD, TYPE
    private Map<String, ArrayList<String>> USERS; //Main storage for user info
    private Map<String, ArrayList<String>> PROJECTS;
    private Map<String, Enquiry> ENQUIRIES;

    public Storage(){
        storageController = new StorageController();
        USERS = storageController.readUserFile();
        PROJECTS = storageController.readProjectFile();
        ENQUIRIES = storageController.readEnquiryFile();
    }

    public ArrayList<String> getUserData(String userID){
        return USERS.get(userID);
    }
    public void updateUserData(List<String> userData) { //update with new info
        USERS.replace(userData.get(1), (ArrayList<String>) userData);
    }


    public Map<String, ArrayList<String>> getProject(){
        return PROJECTS;
    }
    public void updateProject(List<String> projectData) {
    }
    public void addProject(ArrayList<String> newProject) {
        PROJECTS.put(newProject.get(0), newProject);
    }
    public void registerProject(String userID, String projectName) {
        List<String> OfficerList = new ArrayList<>();
        if (PROJECTS.containsKey(projectName)) {
            ArrayList<String> projectInfo = PROJECTS.get(projectName);
            projectInfo.add(userID);
        }

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

    /*
    Call When closing Application
     */
    public void close(){
        storageController.writeUserFile(USERS);
        storageController.writeProjectFile(PROJECTS);
        storageController.writeEnquiryFile(ENQUIRIES);
    }
}
