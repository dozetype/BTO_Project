package system;

import storage.IStorage;
import java.util.ArrayList;

public class Login{
    private ArrayList<String> userData = new ArrayList<>();

    /**
     * For Authenticating the User login details
     * @param userID User Entered NRIC
     * @param password User Entered Password
     * @param storage Reference to the database
     * @return The user detail from the database
     */
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
