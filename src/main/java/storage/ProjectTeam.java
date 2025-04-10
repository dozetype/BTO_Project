package storage;

import users.HDBManager;
import users.HDBOfficer;

import java.util.List;

public class ProjectTeam {
    private String[] officers;
    private final String Manager;
    private String[] officerRegistration;

    public ProjectTeam(String[] officers, String slots, String hdbManager, String[] officerRegistration) {
        this.officers = officers;
        this.Manager = hdbManager;
        this.officerRegistration = officerRegistration;
    }

    public String[] getOfficers() {
        return officers;
    }
    public String getManager() {
        return Manager;
    }
//    public List<String> getOfficerRegistration() {
//        return officerRegistration;
//    }
}
