package users;

import storage.Storage;

public interface IApplicant {
    void viewProject();
//    void applyProject();
//    void viewApplication();
//    void bookFlat();
//    void withdrawApplication();
    void addEnquiry(Storage storage);
    void removeEnquiry(Storage storage);
    void viewEnquiries(Storage storage);
}
