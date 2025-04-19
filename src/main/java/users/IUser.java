package users;

import java.util.List;

public interface IUser {

    /**
     * @param newPassword The new password that user wants to change to
     */
    void changePassword(String newPassword);

    void addFilter();

    void viewFilters();

    void removeFilter();

    void setPassword(String s);

    List<String> getAllUserData();

    String getName();

    String getUserType();
}
