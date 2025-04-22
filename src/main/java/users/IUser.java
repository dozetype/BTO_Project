package users;

import java.util.List;

public interface IUser {

    /**
     * @param newPassword The new password that user wants to change to
     */
    void changePassword(String newPassword);

    /**
     * Adds a new filter for the current User
     */
    void addFilter();

    /**
     * View all the filter that the current User is having
     */
    void viewFilters();

    /**
     * Remove filter of 1 type from current User
     */
    void removeFilter();

    /**
     * @return Details of current User
     */
    List<String> getAllUserData();

    /**
     * @return String Name of the User
     */
    String getName();

    /**
     * @return String of the current User type
     */
    String getUserType();
}
