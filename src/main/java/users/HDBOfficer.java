package users;

import storage.Storage;
import ui.Messages;

import java.util.List;

public class HDBOfficer extends Applicant {
    public HDBOfficer(List<String> userData, Storage storage) {
        super(userData, storage);
        setUserType("Officer");
    }

    @Override
    public void menu() {
        int choice;
        do {
            System.out.println("Hello "+getName()+", "+getUserType()+"\n"+ Messages.APPLICANT_MENU);
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
