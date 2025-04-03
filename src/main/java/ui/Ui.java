package ui;

import java.util.Scanner;

public class Ui {
    private Scanner scanner;

    public Ui(){
        scanner = new Scanner(System.in);
    }

    //Won't accept empty input
    public String readInput(){
        String input = scanner.nextLine();
        while(input.isEmpty()){
            input = scanner.nextLine();
        }
        return input;
    }

    //For reading userID
    public String readUserID(){
        System.out.println("Enter User ID:");
        return readInput();
    }

    //For reading userID
    public String readPassword(){
        System.out.println("Enter Password:");
        return readInput();
    }
}
