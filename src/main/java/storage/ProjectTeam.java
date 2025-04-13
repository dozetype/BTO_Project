package storage;

import java.util.ArrayList;
import java.util.List;

public class ProjectTeam {
    private final String manager;
    private final int maxSlots;
    private List<String> officers;
    private List<String> officersApplying;

    public ProjectTeam(String hdbManager, String slots, String[] officers, String[] officersApplying) {
        this.manager = hdbManager;
        this.maxSlots = Integer.parseInt(slots);
        this.officers = new ArrayList<>();
        for (String id : officers) { //remove empty string
            id = id.trim();
            if (!id.isEmpty()) {
                this.officers.add(id);
            }
        }

        this.officersApplying = new ArrayList<>();
        for (String id : officersApplying) {
            id = id.trim();
            if (!id.isEmpty()) {
                this.officersApplying.add(id);
            }
        }

        System.out.println("officersApplying (constructor): " + this.officersApplying);
    }

    public List<String> getListOfStrings() {
        List<String> list = new ArrayList<>();
        list.add(manager);
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

    public void addOfficerApplying(String officerID) {
        officersApplying.add(officerID);
        System.out.println(officersApplying);
    }
    /**
     * @return NRIC of Manager
     */
    public String getManager() {
        return manager;
    }
//    public List<String> getOfficerRegistration() {
//        return officerRegistration;
//    }
}
