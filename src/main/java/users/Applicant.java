package users;
import storage.Storage;
import ui.Messages;
import ui.Ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Applicant extends User {
    Ui ui = new Ui();
    Storage storage;
    public Applicant(List<String> userData, Storage storage) {
        super(userData);
        this.storage = storage;
    }

    @Override
    public void menu(){
        int choice;
        do {
            System.out.println(Messages.APPLICANT_MENU);
            choice = ui.inputInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter filter to add: ");
                    setFilterList(ui.inputString());
                    break;
                case 2:
                    System.out.println("Current Filters: ");
                    getFilterList();
                    break;
                case 3:
                    System.out.println("Remove Filter");
                    getFilterList();
                    removeFilter(ui.inputInt());
                    break;
                case 4:
                    System.out.print("Enter New Password: ");
                    setPassword(ui.inputString());
                    break;
                case 5:
                    viewProject();
                    break;
            }
        }while(choice < 10);
    }

    private void viewProject(){
        int count=1;
        for (ArrayList i : storage.getProject().values()) {
            System.out.print(count++ +") ");
            i.forEach(item -> System.out.print(item + " | "));
        }
        System.out.println();
    }

}
