package users;

public abstract class Users {
    private String userID;
    private String password;
    private int age;
    private MaritalStatus maritalStatus;

    public Users(String userID, String password, int age, MaritalStatus maritalStatus) {
        this.userID = userID;
        this.password = password;
        this.age = age;
        this.maritalStatus = maritalStatus;
    }

    public void changePassword() {
        //TODO add change password functionality
        return;
    }

    //Accessor and Mutators
    public String getUserID(){
        return this.userID;
    }
    public String getPassword(){
        return this.password;
    }
    public int getAge(){
        return this.age;
    }
    public MaritalStatus getMaritalStatus(){
        return this.maritalStatus;
    }
    public void setUserID(String userID){
        this.userID = userID;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setAge(int age){
        this.age = age;
    }
    public void setMaritalStatus(MaritalStatus maritalStatus){
        this.maritalStatus = maritalStatus;
    }
}
