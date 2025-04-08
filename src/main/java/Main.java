import storage.Storage;
import system.Login;
import ui.*;
import users.*;

import java.util.List;
import java.util.Objects;

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
    }

    private void runUntilQuit(){
        while(!Objects.equals(ui.switchOff(), "0")) {
            Login login = new Login();
            while (currUserData == null) {
                currUserData = login.fetchDatabase(ui.readUserID(), ui.readPassword(), storage);
                if (currUserData == null) {
                    System.out.println("Invalid credentials!");
                }
            }
            if (currUserData.get(5).equals("Manager")) {
                currUser = new HDBManager(currUserData, storage);
            } else if (currUserData.get(5).equals("Officer")) {
                currUser = new HDBOfficer(currUserData, storage);
            } else {
                currUser = new Applicant(currUserData, storage);
            }

            currUser.menu();
            System.out.printf("Hello, %s, %s!\n", currUserData.get(0), currUserData.get(5));
            storage.updateUser(currUser.close(currUserData.get(5)));
        }
    }

    private void exit(){
        storage.close(); //Override UserList.csv
        System.exit(0);
    }
}