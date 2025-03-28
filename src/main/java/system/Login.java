package system;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Login {
    String userID;
    String password;
    String[] data = new String[5];
    Scanner sc = new Scanner(System.in);
    public Login() {
        do {
            System.out.println("Enter User ID:");
            userID = sc.nextLine();
            System.out.println("Enter Password:");
            password = sc.nextLine();
            data = checkData(userID, password);
            if(data[0] == null)
                System.out.println("Invalid User ID or Password!");
        } while (data[0] == null);
    }

    private String[] checkData(String userID, String password){
        try {
            Scanner data = new Scanner(new File("src/main/user_data/ApplicantList.csv"));
            data.nextLine(); //for skipping the title
            System.out.println();

            while (data.hasNextLine()) {// Going through all user data
                String line = data.nextLine();
                String[] row = line.split(",");
                if(row.length==5 && row[1].equals(userID) && row[4].equals(password))
                    return row;
            }
            data.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return new String[5];
    }

    public String[] getData(){
        return data;
    }
}
