package storage;

import java.util.*;

public class Storage {
    StorageController storageController;
    //NAME, USER_ID, AGE, MARITAL, PASSWORD, TYPE
    private Map<String, ArrayList<String>> USERS; //Main storage for user info
    private Map<String, ArrayList<String>> PROJECTS;

    public Storage(){
        storageController = new StorageController();
        USERS = storageController.readUserFile();
        PROJECTS = storageController.readProjectFile();
    }

    public ArrayList<String> getUserData(String userID){
        return USERS.get(userID);
    }

    public void close(){
        storageController.writeUserFile(USERS);
    }

    public Map<String, ArrayList<String>> getProject(){
        return PROJECTS;
    }
    public void updateUser(List<String> userData) { //update with new info
        USERS.replace(userData.get(1), (ArrayList<String>) userData);
    }
}
