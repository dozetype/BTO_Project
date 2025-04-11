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

    /*
    Initialise the ENQUIRIES data
     */
    public Map<String, Enquiry> readEnquiryFile();

    /*
    Adding Enquiry while in operation
     */
    public Map<String, Enquiry> addEnquiry(String askerID, String projectName, String question, Map<String, Enquiry> ENQUIRIES);

    /*
    Write ENQUIRIES into csv
     */
    public void writeEnquiryFile(Map<String, Enquiry> ENQUIRY); //WRITES using ENQUIRY in Storage


    /**
     * Initialise PROJECTS data by reading the BTOApplication.csv
     * @return HashMap with key: ApplicantID Name, Value: Project Details
     */
    public Map<String, BTOApplication> readBTOApplicationFile();

    public void writeBTOApplicationFile(Map<String, BTOApplication> BTOAPPLICATION);
}
