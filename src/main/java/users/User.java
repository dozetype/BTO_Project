package users;

import ui.Messages;
import ui.Ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class User implements IUser {
    private final String name; //0
    private final String userID; //1
    private final int age; //2
    private final MaritalStatus maritalStatus; //3
    private String password; //4
    private String userType;
    private Map<String, String> filters = new HashMap<>(); //Key:Type of filter, Value: their filter
    private Ui ui = new Ui();

    public User(List<String> userData, String userType) {
        this.name = userData.get(0);
        this.userID = userData.get(1);
        this.age = Integer.parseInt(userData.get(2));
        this.maritalStatus = MaritalStatus.valueOf(userData.get(3).toUpperCase());
        this.password = userData.get(4);
        this.userType = userType;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    /**
     * Add one filter for the User
     */
    public void addFilter(){ //adds 1 filter
        System.out.println("Which type of filter do you want to add?\n" + Messages.FILTER_MENU);
        switch (ui.inputInt()){
            case 1:
                System.out.println("Enter which Neighbourhood to filter: ");
                filters.put("NEIGHBOURHOOD", ui.inputString());
                break;
            case 2:
                System.out.println("Enter which Room Type to filter\n1: 2 Rooms\n2: 3 Rooms");
                int choice = ui.inputInt();
                if(choice == 1)
                    filters.put("FLAT_TYPE", "TWO_ROOM");
                else if(choice == 2)
                    filters.put("FLAT_TYPE", "THREE_ROOM");
                else
                    System.out.println("Invalid Choice. Exiting.");
                break;
            default:
                System.out.println("Invalid filter choice. Exiting.");
                break;
        }
    }

    public void removeFilter(){ //removes 1 element
        System.out.println("Which type of filter do you want to remove?\n" + Messages.FILTER_MENU);
        switch (ui.inputInt()){
            case 1:
                filters.remove("NEIGHBOURHOOD");
                break;
            case 2:
                filters.remove("FLAT_TYPE");
                break;
            default:
                System.out.println("Invalid filter choice. Exiting.");
                return;
        }
        System.out.println("REMOVED FILTER");
    }

    public void viewFilters(){
        for(Map.Entry<String, String> entry : filters.entrySet()){
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public String getFilter(String key){
        return filters.get(key);
    }

    /**
     * @return filter with key
     */
    public Map<String, String> getFilters(){ return filters; }


    //Accessor and Mutators
    public String getName(){ return this.name; }
    public String getUserID(){ return this.userID; }
    public int getAge(){ return this.age; }
    public MaritalStatus getMaritalStatus(){ return this.maritalStatus; }
    public String getPassword(){ return this.password; }
    public String getUserType(){ return this.userType; }
    public List<String> getAllUserData(){
        List<String> list = new ArrayList<>();
        list.add(name);
        list.add(userID);
        list.add(String.valueOf(age));
        list.add(maritalStatus.toString());
        list.add(password);
        list.add(userType);
        return list;
    }

    public void setPassword(String password){
        this.password = password;
    }
    public void setUserType(String userType){
        this.userType = userType;
    }
}
