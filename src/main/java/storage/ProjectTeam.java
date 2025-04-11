package storage;

import users.HDBManager;
import users.HDBOfficer;

import java.util.ArrayList;
import java.util.List;

public class ProjectTeam {
    private final String Manager;
    private final int maxSlots;
    private String[] officers;
    private String[] officerRegistration; //TODO maybe load this from using another project.csv

    public ProjectTeam(String hdbManager, String slots, String[] officers, String[] officerRegistration) {
        this.officers = officers;
        this.maxSlots = Integer.parseInt(slots);
        this.Manager = hdbManager;
        this.officerRegistration = officerRegistration;
    }

    public List<String> getListOfStrings() {
        List<String> list = new ArrayList<>();
        list.add(Manager);
        list.add(String.valueOf(maxSlots));
        list.add("\""+String.join(".", officers)+"\"");
        return list;
    }
    /**
     * @return NRIC of Officers
     */
    public String[] getOfficers() {
        return officers;
    }

    /**
     * @return NRIC of Manager
     */
    public String getManager() {
        return Manager;
    }
//    public List<String> getOfficerRegistration() {
//        return officerRegistration;
//    }
}
