package users;

import storage.Project;
import storage.Storage;

public interface IApplicant {
    void viewProject(Storage st);
    void applyProject(Storage st);
    void viewApplication(Storage st);
//    void bookFlat(Storage st);
//    void withdrawApplication(Storage st);
    void addEnquiry(Storage st);
    void removeEnquiry(Storage st);
    void viewEnquiries(Storage st);
}
