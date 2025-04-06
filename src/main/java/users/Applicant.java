package users;

import ui.Messages;

import java.util.List;

public class Applicant extends User {
    public Applicant(List<String> userData) {
        super(userData);
        System.out.println(getMaritalStatus());
    }

    @Override
    public void menu() {
        return;
    }

}
