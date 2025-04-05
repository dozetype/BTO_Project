package users;

public class Applicant extends User {
    public Applicant(String userID, String password, int age, MaritalStatus maritalStatus) {
        super(userID, password, age, maritalStatus);
        System.out.println(getPassword());
    }
}
