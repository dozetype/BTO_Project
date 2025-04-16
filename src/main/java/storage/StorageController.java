package storage;

import java.io.*;
import java.util.*;

public class StorageController implements IStorageController {
    int nextEnquiryID;
    int nextBTOApplicationID;


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
            bw.write("Project Name,Neighborhood,Type 1,Type 1 Units,Type 1 Price,Type 2,Type 2 Units,Type 2 Price,Opening date,Closing date,Manager,Officer Slot,Officer,OFFICER APPLYING,OFFICER REJECTED,VISIBILITY\n");
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
        for (String e : ENQUIRIES.keySet()) //Go through the EnquiryID and take the Largest
            nextEnquiryID = Math.max(nextEnquiryID, Integer.parseInt(e)) + 1;
        return ENQUIRIES;
    }

    public void addEnquiry(String askerID, String projectName, String question, Map<String, Enquiry> ENQUIRIES) {
        String ID = Integer.toString(nextEnquiryID);
        ENQUIRIES.put(ID, new Enquiry(ID, askerID, projectName, question, "NULL"));
        nextEnquiryID++;
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
                .forEach(line -> BTOAPPLICATIONS.put(line[0], new BTOApplication(line[0], line[1], line[2], line[3], line[4], line[5], line[6])));
        for (String a : BTOAPPLICATIONS.keySet()) //Go through the ID and take the Largest
            nextBTOApplicationID = Math.max(nextBTOApplicationID, Integer.parseInt(a)) + 1;
        return BTOAPPLICATIONS;
    }

    public void addBTOApplication(String userID, String projectName, String price, String type, Map<String, BTOApplication> BTOAPPLICATIONS) {
        String ID = Integer.toString(nextBTOApplicationID);
        BTOAPPLICATIONS.put(ID, new BTOApplication(ID, userID, projectName, price, "NULL", type, "PENDING"));
        nextBTOApplicationID++;
    }

    public void writeBTOApplicationFile(Map<String, BTOApplication> BTOAPPLICATIONS){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/database/BTOApplication.csv"))){
            bw.write("BTOApplicationID,ApplicantID,ProjectName,Price,OfficerID,FLAT TYPE,STATUS\n");
            for (BTOApplication a : BTOAPPLICATIONS.values())  //using info in USERS to write file
                bw.write(a.getID()+","+a.getApplicantID()+","+a.getProjectName()+","+a.getPrice()+","+a.getOfficerInCharge()+","+a.getFlatType()+","+a.getApplicationStatus()+"\n");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}