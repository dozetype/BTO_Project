package users;

import storage.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IApplicant {
    /**
     * User can see all project listing if its open and its visible
     * If User have applied before they should still be able to see the listing even after Time Frame
     * @param st
     */
    public void viewBTOProject(IStorage st);

    /**
     * Only able to View and Apply for BTO Project if they have not applied or if their previous application was Unsuccessful or Withdrawn
     * @param st
     */
    public void applyBTOProject(IStorage st);

    /**
     *View All BTO Applications made by user
     * @param st DataBase
     */
    public void viewBTOApplication(IStorage st);

    /**
     * @param storage DataBase
     */
    public void viewEnquiries(IStorage storage);

    /**
     * @param storage DataBase
     */
    public void addEnquiry(IStorage storage);

    /**
     * Can Only Delete the Enquiry if it has not been replied
     *
     * @param storage DataBase
     */
    public void removeEnquiry(IStorage storage);

    /**
     * User can only edit their enquiry if it hasn't been replied
     * @param st
     */
    public void editEnquiry(IStorage st);

    public void withdrawBTOApplication(IStorage st);
}
