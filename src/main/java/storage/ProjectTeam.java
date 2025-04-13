package storage;

import users.HDBManager;
import users.HDBOfficer;

import java.util.ArrayList;
import java.util.List;

public class ProjectTeam {
    private final String Manager;
    private final int maxSlots;
    private List<String> officers;
    private List<String> officersApplying; //TODO maybe load this from using another project.csv

    public ProjectTeam(String hdbManager, String slots, String[] officers, String[] officersApplying) {
        this.Manager = hdbManager;
        this.maxSlots = Integer.parseInt(slots);
        this.officers = List.of(officers);
        this.officersApplying = List.of(officersApplying);
    }

    public List<String> getListOfStrings() {
        List<String> list = new ArrayList<>();
        list.add(Manager);
        list.add(String.valueOf(maxSlots));
        list.add("\""+String.join(".", officers)+"\"");
        list.add("\""+String.join(".", officersApplying)+"\"");
        return list;
    }
    /**
     * @return NRIC of Officers
     */
    public List<String> getOfficers() {
        return officers;
    }
    public List<String> getOfficersApplying() {
        return officersApplying;
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
