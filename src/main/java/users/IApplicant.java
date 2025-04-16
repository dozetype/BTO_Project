package users;

import storage.Storage;

public interface IApplicant {
    void viewBTOProject(Storage st);
    void applyBTOProject(Storage st);
    void viewApplication(Storage st);
//    void bookFlat(Storage st);
    void withdrawBTOApplication(Storage st);
    void viewEnquiries(Storage st);
    void addEnquiry(Storage st);
    void removeEnquiry(Storage st);
    void editEnquiry(Storage st);
}
