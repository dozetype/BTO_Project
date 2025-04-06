package users;

import java.util.List;

public class HDBManager extends User{
    public HDBManager(List<String> userData) {
        super(userData);
    }

    @Override
    public void menu() {
        return;
    }
}
