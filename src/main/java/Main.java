import storage.Storage;
import system.Login;
import ui.Messages;
import ui.Ui;

import java.util.List;

public class Main{
    List<String> userData;
    private Ui ui;

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
        Storage storage = new Storage();
        Login login = new Login();
        while(userData == null) {
            userData = login.fetchDatabase(ui.readUserID(), ui.readPassword(), storage);
            if(userData == null){
                System.out.println("Invalid credentials!");
            }
        }
        System.out.println(userData);
    }

    private void runUntilQuit(){
        System.out.println("Getting Data...");
        System.out.printf("Hello, %s, %s!\n", userData.get(0), userData.get(5));

    }

    private void exit(){
        System.exit(0);
    }
}