import storage.Storage;
import system.Login;
import ui.*;
import users.*;

import java.util.List;

public class Main{
    List<String> currUserData;
    User currUser;
    private Ui ui;
    private Storage storage;

    public static void main(String[] args){
        System.out.println(Messages.APPLICATION_NAME);
        new Main().run();
    }

    public void run(){
        initialize();
        runUntilQuit();
        exit();
    }

    private void initialize(){
        ui = new Ui();
        storage = new Storage();
        storage.readFile();
        Login login = new Login();

        while(currUserData == null) {
            currUserData = login.fetchDatabase(ui.readUserID(), ui.readPassword(), storage);
            if(currUserData == null){
                System.out.println("Invalid credentials!");
            }
        }
        if(currUserData.get(5).equals("Applicant")) {currUser = new Applicant(currUserData); }
        else if(currUserData.get(5).equals("Officer")) {currUser = new HDBOfficer(currUserData); }

        currUser.menu();
        System.out.println(currUserData);
    }

    private void runUntilQuit(){
        System.out.println("Getting Data...");
        System.out.printf("Hello, %s, %s!\n", currUserData.get(0), currUserData.get(5));

    }

    private void exit(){
        storage.updateUser(currUser.close(currUserData.get(5)));
        storage.writeFile(); //Override UserList.csv
        System.exit(0);
    }
}