package system;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Login {
    String userID;
    String password;
    String[] userTypes = {"Applicant", "Manager", "Officer"};
    ArrayList<String> userData = new ArrayList<>();
    Scanner sc = new Scanner(System.in);
    public Login() {
        do {
            System.out.println("Enter User ID:");
            userID = sc.nextLine();
            System.out.println("Enter Password:");
            password = sc.nextLine();
            checkDatabase(userID, password);
            if(userData.isEmpty())
                System.out.println("Invalid User ID or Password!");
        } while (userData.isEmpty());
    }

    private void checkDatabase(String userID, String password){
        try {
            int type = 0;
            Scanner data = new Scanner(new File("src/main/user_data/ApplicantList.csv"));
            data.nextLine(); //for skipping the title
            System.out.println();

            while (data.hasNextLine()) {// Going through all user data
                String line = data.nextLine();
                String[] row = line.split(",");
                if(row.length==1) type++;
                else if(row.length==5 && row[1].equals(userID) && row[4].equals(password)) {
                    Collections.addAll(userData, row); //adding basic details into userData
                    userData.add(userTypes[type]); //adding type
                }
            }
            data.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public ArrayList<String> getData(){
        return userData;
    }
}
