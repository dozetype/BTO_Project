package storage;

import java.io.*;
import java.util.*;

public class Storage {
    //NAME, USER_ID, AGE, MARITAL, PASSWORD, TYPE
    private Map<String, ArrayList<String>> USERS = new HashMap<>(); //Main storage for user info

    public void readFile(){
        try{
            int type = 0;
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
    }

    public ArrayList<String> getUserData(String userID){
        return USERS.get(userID);
    }

    public void writeFile(){ //Replace userList.csv
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

    public void updateUser(List<String> userData) { //update with new info
        USERS.replace(userData.get(1), (ArrayList<String>) userData);
    }
}
