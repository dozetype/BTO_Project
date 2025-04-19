package storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IStorage {
    /**
     * @param userID Users NRIC
     * @return The Users basic details
     */
    public ArrayList<String> getUserData(String userID);

    /**
     * Update basic detail of the User
     * @param userData List of new basic details of User
     */
    public void updateUserData(List<String> userData);

    /**
     * @return PROJECTS
     */
    public Map<String, Project> getProject();

    /**
     * Update data of indicated Project
     * @param projectData
     */
    public void updateProject(List<String> projectData);

    /**
     * Add a New Project
     * @param newProject
     */
    public void addProject(Project newProject);

    /**
     * Remove indicated Project
     * @param projectName
     */
    public void removeProject(String projectName);

    /**
     * Add a new Officer into Officer applying inside projectTeam
     * @param userID
     * @param projectName
     */
    public void registerProject(String userID, String projectName);

    /**
     * Returns Project by using it's project name
     * @param projectName
     * @return
     */
    public Project getProjectByName(String projectName);

    /**
     * @return ArrayList of Project
     */
    public List<Project> getAllProjects();

    /**
     * Returns the Project that the Manager is handling
     * @param managerID
     * @return
     */
    public List<Project> getProjectsByManager(String managerID);

    public Map<String, Enquiry> getEnquiries();
    public void updateEnquiries(String ID, Enquiry newEnquiry);
    public void addEnquiries(String askerID, String projectName, String question);
    public void removeEnquiries(String ID);


    public Map<String, BTOApplication> getBTOApplications();

    public BTOApplication getApplicantApplicationByID(String userID);

    public void addBTOApplication(String userID, String projectName, String price, String type);

    public List<Project> getFilteredProjects(Map<String, String> filters);


    /**
     * Called when quitting the Application
     */
    public void close();
}
