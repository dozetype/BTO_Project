package users;

import storage.Storage;

import java.util.List;

public class HDBManager extends User{
//    private Storage storage;
    public HDBManager(List<String> userData) {
        super(userData, "Manager");
    }
}
