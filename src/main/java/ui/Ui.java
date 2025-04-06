package ui;

import java.util.Scanner;

public class Ui {
    private Scanner scanner;

    public Ui(){
        scanner = new Scanner(System.in);
    }

    public String inputString(){
        String input = scanner.nextLine();
        while(input.isEmpty()){ //Won't accept empty input
            input = scanner.nextLine();
        }
        return input;
    }

    //Print and Ask
    public String readUserID(){
        System.out.println("Enter User ID:");
        return inputString();
    }

    public String readPassword(){
        System.out.println("Enter Password:");
        return inputString();
    }

    public String readNewPassword(){
        System.out.println("Enter New Password:");
        return inputString();
    }
}
