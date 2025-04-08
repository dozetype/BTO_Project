package storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.*;

public class StorageController {
    public Map<String, ArrayList<String>> readUserFile(){
        Map<String, ArrayList<String>> USERS = new HashMap<>();
        try{
            Scanner data = new Scanner(new File("src/main/user_data/UserList.csv"));
            data.nextLine(); //for skipping the title
            while(data.hasNextLine()) {// Going through all user data
                String line = data.nextLine();
                String[] row = line.split(","); //delimiting
                USERS.put(row[1], new ArrayList<>(Arrays.asList(row))); //appending into hashmap
            }
            data.close();
        }
        catch (FileNotFoundException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return USERS;
    }

    public void writeUserFile(Map<String, ArrayList<String>> USERS){ //Replace userList.csv
        try{
            BufferedWriter obj = new BufferedWriter(new FileWriter("src/main/user_data/UserList.csv"));
            obj.write("Name,NRIC,Age,Marital Status,Password,Type\n");
            for (ArrayList<String> user : USERS.values()) { //using info in USERS to write file
                String line = String.join(",", user) + "\n";
                obj.write(line);
            }
            obj.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Map<String, ArrayList<String>> readProjectFile(){
        Map<String, ArrayList<String>> PROJECTS = new HashMap<>();
        try{
            Scanner data = new Scanner(new File("src/main/user_data/ProjectList.csv"));
            data.nextLine(); //for skipping the title
            while(data.hasNextLine()) {// Going through all user data
                String line = data.nextLine();
                String[] row = line.split(","); //delimiting
                PROJECTS.put(row[0], new ArrayList<>(Arrays.asList(row))); //appending into hashmap
            }
            data.close();
        }
        catch (FileNotFoundException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return PROJECTS;
    }
}
