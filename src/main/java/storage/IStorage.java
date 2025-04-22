package storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IStorage {
    /**
     * @param userID Users NRIC
     * @return The Users basic details
     */
    ArrayList<String> getUserData(String userID);

    /**
     * Update basic detail of the User
     * @param userData List of new basic details of User
     */
    void updateUserData(List<String> userData);

    /**
     * @return PROJECTS
     */
    Map<String, Project> getProject();

    /**
     * Update data of indicated Project
     * @param projectData
     */
    void updateProject(List<String> projectData);

    /**
     * Adds a New Project Object
     * @param newProject
     */
    void addProject(Project newProject);

    /**
     * Remove indicated Project
     * @param projectName
     */
    void removeProject(String projectName);

    /**
     * Add a new Officer into Officer applying inside projectTeam
     * @param userID
     * @param projectName
     */
    void registerProject(String userID, String projectName);

    /**
     * Returns Project by using its project name
     * @param projectName
     * @return
     */
    Project getProjectByName(String projectName);

    /**
     * @return ArrayList of Project
     */
    List<Project> getAllProjects();

    /**
     * Returns the Project that the Manager is handling
     * @param managerID
     * @return
     */
    List<Project> getProjectsByManager(String managerID);

    Map<String, Enquiry> getEnquiries();
    void updateEnquiries(String ID, Enquiry newEnquiry);
    void addEnquiries(String askerID, String projectName, String question);
    void removeEnquiries(String ID);


    Map<String, BTOApplication> getBTOApplications();

    BTOApplication getApplicantApplicationByID(String userID);

    /**
     * Add a new BTOApplication Object into storage
     * @param userID NRIC
     * @param projectName Name of Project
     * @param price price of the flat
     * @param type Type of the flat
     */
    void addBTOApplication(String userID, String projectName, String price, String type);

    /**
     * Return List of Projects that are filtered
     * @param filters Map of filters of user
     * @return List of Projects after filtering
     */
    List<Project> getFilteredProjects(Map<String, String> filters);


    /**
     * Called when quitting the Application
     */
    void close();
}
