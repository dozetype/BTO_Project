package users;

import java.util.ArrayList;
import java.util.List;

public abstract class User {
    private String name; //0
    private String userID; //1
    private String age; //2
    private MaritalStatus maritalStatus; //3
    private String password; //4
    private ArrayList<String> filterList = new ArrayList<>(); //Store individual users filter

    public User(List<String> userData) {
        this.name = userData.get(0);
        this.userID = userData.get(1);
        this.age = userData.get(2);
        this.maritalStatus = MaritalStatus.valueOf(userData.get(3).toUpperCase());
        this.password = userData.get(4);
    }

    public void menu(){ //overridden by children
        return;
    }

    public void changePassword(String newPassword) {
        //TODO add change password functionality
        this.password = newPassword;
    }

    public List<String> close(String type){ //return user data
        List<String> data = new ArrayList<>();
        data = getAll();
        data.add(type); //add user type
        return data;
    }

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
    public String getAge(){ return this.age; }
    public MaritalStatus getMaritalStatus(){ return this.maritalStatus; }
    public String getPassword(){ return this.password; }
    public List<String> getAll(){
        List<String> list = new ArrayList<>();
        list.add(name);
        list.add(userID);
        list.add(age);
        list.add(maritalStatus.toString());
        list.add(password);
        return list;
    }

    public void setUserID(String userID){ this.userID = userID; }
    public void setPassword(String password){
        this.password = password;
    }
    public void setAge(String age){
        this.age = age;
    }
    public void setMaritalStatus(MaritalStatus maritalStatus){
        this.maritalStatus = maritalStatus;
    }
}
