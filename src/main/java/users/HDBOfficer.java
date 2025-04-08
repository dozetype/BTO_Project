package users;

import storage.Storage;

import java.util.List;

public class HDBOfficer extends Applicant {
    public HDBOfficer(List<String> userData, Storage storage) {
        super(userData, storage);
    }

    @Override
    public void menu() {
        int choice;
        do {
            System.out.println("Welcome to the Officer Menu");
            System.out.println("1. Add Filter");
            System.out.println("2. View Filters");
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
            }
        }while(choice < 10);
    }

}
