package system;

import storage.IStorage;

import java.util.ArrayList;

public class Login{
    ArrayList<String> userData = new ArrayList<>();

    public ArrayList<String> authenticate(String userID, String password, IStorage storage){
        if(storage.getUserData(userID) != null){ //check if ID exist
            userData = storage.getUserData(userID);
            if(userData.get(4).equals(password)){ //check password correct
                return userData;
            }
        }
        return null;
    }
}
