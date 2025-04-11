package storage;

import java.io.*;
import java.util.*;

public class StorageController implements IStorageController {
    int nextEnquiryID;


    public List<String[]> readCSV(String filePath) {
        List<String[]> data = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line.split(","));
            }
        }catch(IOException e){
            System.out.println("File not found");
        }
        return data;
    }

    public Map<String, ArrayList<String>> readUserFile(){
        Map<String, ArrayList<String>> USERS = new HashMap<>();
        readCSV("src/main/database/UserList.csv").stream().skip(1)
                .forEach(user -> USERS.put(user[1], new ArrayList<>(Arrays.asList(user))));
        return USERS;
    }

    public void writeUserFile(Map<String, ArrayList<String>> USERS){ //Replace userList.csv
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/database/UserList.csv"))){
            bw.write("Name,NRIC,Age,Marital Status,Password,Type\n");
            for (ArrayList<String> user : USERS.values()) { //using info in USERS to write file
                String line = String.join(",", user) + "\n";
                bw.write(line);
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Map<String, Project> readProjectFile(){
        Map<String, Project> PROJECTS = new HashMap<>();
        readCSV("src/main/database/ProjectList.csv").stream().skip(1)
                .forEach(line -> PROJECTS.put(line[0], new Project(line)));
        return PROJECTS;
    }

    public void writeProjectFile(Map<String, Project> PROJECT){ //Replace userList.csv
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/database/ProjectList.csv"))){
            bw.write("Project Name,Neighborhood,Type 1,Number of units for Type 1,Selling price for Type 1,Type 2,Number of units for Type 2,Selling price for Type 2,Application opening date,Application closing date,Manager,Officer Slot,Officer,VISIBILITY\n");
            for (Project p : PROJECT.values()) { //using info in USERS to write file
                String line = String.join(",", p.getListOfStrings()) + "\n";
                bw.write(line);
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Map<String, Enquiry> readEnquiryFile(){
        Map<String, Enquiry> ENQUIRIES = new HashMap<>();
        readCSV("src/main/database/Enquiry.csv").stream().skip(1)
                .forEach(line -> ENQUIRIES.put(line[0], new Enquiry(line[0], line[1], line[2], line[3], line[4])));
        for (String e : ENQUIRIES.keySet())
            nextEnquiryID = Math.max(nextEnquiryID, Integer.parseInt(e)) + 1;
        return ENQUIRIES;
    }

    public Map<String, Enquiry> addEnquiry(String askerID, String projectName, String question, Map<String, Enquiry> ENQUIRIES) {
        String ID = Integer.toString(nextEnquiryID);
        ENQUIRIES.put(ID, new Enquiry(ID, askerID, projectName, question, "NULL"));
        nextEnquiryID++;
        return ENQUIRIES;
    }

    public void writeEnquiryFile(Map<String, Enquiry> ENQUIRY){ //WRITES using ENQUIRY in Storage
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/database/Enquiry.csv"))){
            bw.write("ID,Asker ID,Project Name,Question,Reply\n");
            for (Enquiry e : ENQUIRY.values()) { //using info in USERS to write file
                bw.write(e.getID()+","+e.getAskerID()+","+e.getProjectName()+","+e.getQuestion()+","+e.getReply()+"\n");
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Map<String, BTOApplication> readBTOApplicationFile(){
        Map<String, BTOApplication> BTOAPPLICATIONS = new HashMap<>();
        readCSV("src/main/database/BTOApplication.csv").stream().skip(1)
                .forEach(line -> BTOAPPLICATIONS.put(line[0], new BTOApplication(line[0], line[1], line[2], line[3], line[4], line[5])));
        return BTOAPPLICATIONS;
    }

    public void writeBTOApplicationFile(Map<String, BTOApplication> BTOAPPLICATIONS){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/database/BTOApplication.csv"))){
            bw.write("ApplicantID,ProjectName,Price,OfficerID,FLAT TYPE,STATUS\n");
            for (BTOApplication a : BTOAPPLICATIONS.values())  //using info in USERS to write file
                bw.write(a.getApplicantID()+","+a.getProjectName()+","+a.getPrice()+","+a.getOfficerInCharge()+","+a.getFlatType()+","+a.getApplicationStatus()+"\n");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}