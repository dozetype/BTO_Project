import system.Login;

public class Main {
    //Add in attributes
    String[] userData;
    public static void main(String[] args){
        System.out.println("Welcome to BTO System! \nPlease Login");
        new Main().run();
    }

    public void run(){
        start();
        runUntilQuit();
        exit();
    }

    private void start(){
        Login login = new Login();
        userData = login.getData();
    }

    private void runUntilQuit(){
        System.out.println("Getting Data...");
        System.out.printf("Hello, %s!\n", userData[0]);
        return;
    }

    private void exit(){
        System.exit(0);
    }
}