package users;

public interface IUser {

    /**
     * @param newPassword The new password that user wants to change to
     */
    void changePassword(String newPassword);
}
