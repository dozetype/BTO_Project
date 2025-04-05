package storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Storage {
    //NAME, USER_ID, AGE, MARITAL, PASSWORD, TYPE
    private Map<String, ArrayList<String>> USERS = new HashMap<>(); //Main storage for user info
    String[] userTypes = {"Applicant", "Manager", "Officer"};
    public Storage() {
        try{
            int type = 0;
            Scanner data = new Scanner(new File("src/main/user_data/ApplicantList.csv"));
            data.nextLine(); //for skipping the title
            while(data.hasNextLine()) {// Going through all user data
                String line = data.nextLine();
                String[] row = line.split(","); //delimiting
                if (row.length == 1) type++;
                else {
                    USERS.put(row[1], new ArrayList<>(Arrays.asList(row))); //appending into hashmap
                    USERS.get(row[1]).add(userTypes[type]);
                }
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
}
