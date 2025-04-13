package users;

import java.util.ArrayList;
import java.util.List;

public abstract class User implements IUser {
    private final String name; //0
    private final String userID; //1
    private final int age; //2
    private final MaritalStatus maritalStatus; //3
    private String password; //4
    private String userType;
    private ArrayList<String> filterList = new ArrayList<>(); //Store individual users filter

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
     * @param filter the filter user wants to add
     */
    public void setFilterList(String filter){ //adds 1 filter
        this.filterList.add(filter);
    }
    public void getFilterList(){ //show list of filter
        for(int i = 0; i < this.filterList.size(); i++){
            System.out.println(i+1 + ") " + this.filterList.get(i));
        }
    }
    public void removeFilter(int index){ //removes 1 element
        try {
            this.filterList.remove(index - 1);
        }
        catch(IndexOutOfBoundsException e){
            System.out.println("Filter Index Out of Bounds, Please Try Again");
        }
    }


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
