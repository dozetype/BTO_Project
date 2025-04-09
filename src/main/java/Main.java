import storage.Storage;
import system.Login;
import ui.*;
import users.*;

import java.util.List;
import java.util.Objects;

public class Main{
    List<String> loginUserData;
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
            while (loginUserData == null) {
                loginUserData = login.fetchDatabase(ui.readUserID(), ui.readPassword(), storage);
                if (loginUserData == null) {
                    System.out.println("Invalid credentials!");
                }
            }
            if (loginUserData.get(5).equals("Manager")) {
                currUser = new HDBManager(loginUserData, storage);
            } else if (loginUserData.get(5).equals("Officer")) {
                currUser = new HDBOfficer(loginUserData, storage);
            } else {
                currUser = new Applicant(loginUserData, storage);
            }

            currUser.menu();
            loginUserData = null;//log out
        }
    }

    private void exit(){
        storage.close(); //Override UserList.csv
        System.exit(0);
    }
}