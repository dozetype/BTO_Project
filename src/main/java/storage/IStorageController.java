package storage;

import java.io.*;
import java.util.*;

public interface IStorageController {
    /**
     * Read every line and delimit it
     * @param filePath path to the csv file
     * @return Delimited rows of data
     */
    public List<String[]> readCSV(String filePath);

    /**
     * Initialise USERS data from reading the csv file
     * @return HashMap of Users
     */
    public Map<String, ArrayList<String>> readUserFile();

    /**
     * Write USERS into csv
     * @param USERS HashMap of all Users
     */
    public void writeUserFile(Map<String, ArrayList<String>> USERS);

    /**
     * Initialise PROJECTS data by reading the ProjectList.csv
     * @return HashMap with key: Project Name, Value: Project Details
     */
    public Map<String, Project> readProjectFile();

    /**
     * Write the data in PROJECT into csv
     * @param PROJECT HashMap of Project
     */
    public void writeProjectFile(Map<String, Project> PROJECT);


    /**
     * Initialise the ENQUIRIES data
     * @return Map of Data read
     */
    public Map<String, Enquiry> readEnquiryFile();

    /**
     * Adding Enquiry while in operation
     * @param askerID NRIC of User
     * @param projectName Name of Project
     * @param question The Question
     * @param ENQUIRIES Reference to the variable to
     */
    public void addEnquiry(String askerID, String projectName, String question, Map<String, Enquiry> ENQUIRIES);

    /**
     * Write Enquiry int CSV
     * @param ENQUIRY Map of Enquiry
     */
    public void writeEnquiryFile(Map<String, Enquiry> ENQUIRY); //WRITES using ENQUIRY in Storage


    /**
     * Initialise PROJECTS data by reading the BTOApplication.csv
     * @return HashMap with key: ApplicantID Name, Value: Project Details
     */
    public Map<String, BTOApplication> readBTOApplicationFile();


    /**
     * Adds a new BTO Application by user
     * @param userID Users NRIC
     * @param projectName Name of Project
     * @param price Price of house
     * @param type Room Type
     * @param BTOAPPLICATIONS Reference to the variable adding to
     */
    public void addBTOApplication(String userID, String projectName, String price, String type, Map<String, BTOApplication> BTOAPPLICATIONS);

    /**
     * Write BTOAPPLICATION into CSV
     * @param BTOAPPLICATION Map of BTOApplications
     */
    public void writeBTOApplicationFile(Map<String, BTOApplication> BTOAPPLICATION);
}
