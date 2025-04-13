package storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Project {
    private final String projectName;
    private final String neighbourhood;
    private long openingDate;
    private long closingDate;
    private final String createdBy;
    private HashMap<FlatType, Integer> units = new HashMap<>(); // key: flatType, value: number of units
    private HashMap<FlatType, Integer> prices = new HashMap<>();
    private ProjectTeam projectTeam;
    private boolean projectVisibility;

    public Project(String[] data) {
        /*
        (0)Project Name,(1)Neighborhood,(2)Type 1,(3)Number of units for Type 1,(4)Selling price for Type 1,
        (5)Type 2,(6)Number of units for Type 2,(7)Selling price for Type 2,
        (8)Application opening date,(9)Application closing date,(10)Manager,(11)Officer Slot,(12)Officer,
        (13)Officer Applying,(14)Visibility
         */
        projectName = data[0];
        neighbourhood = data[1];
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            openingDate = formatter.parse(data[8]).getTime();
            closingDate = formatter.parse(data[9]).getTime();
//            System.out.println(openingDate + " " + closingDate + " " + formatter.format(new Date(openingDate)));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        createdBy = data[10];
        units.put(FlatType.valueOf(data[2]), Integer.valueOf(data[3])); prices.put(FlatType.valueOf(data[2]), Integer.valueOf(data[4]));
        units.put(FlatType.valueOf(data[5]), Integer.valueOf(data[6])); prices.put(FlatType.valueOf(data[5]), Integer.valueOf(data[7]));
        String[] officers = data[12].replaceAll("\"", "").split("\\."); //remove "" and split "."
        String[] officerApplying = data[13].replaceAll("\"", "").split("\\."); //remove "" and split "."
        projectTeam = new ProjectTeam(data[10], data[11], officers, officerApplying);
        projectVisibility = Boolean.parseBoolean(data[14]);
    }

    /**
     * Used for turning all attribute in this to Strings
     * @return List of Strings of each attribute
     */
    public List<String> getListOfStrings(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        List<String> list = new ArrayList<>();
        list.add(projectName);
        list.add(neighbourhood);
        for(FlatType key : units.keySet()){
            list.add(String.valueOf(key));
            list.add(String.valueOf(units.get(key)));
            list.add(String.valueOf(prices.get(key)));
        }
        list.add(formatter.format(new Date(openingDate)));
        list.add(formatter.format(new Date(closingDate)));
        list.addAll(projectTeam.getListOfStrings());
        list.add(projectVisibility ? "TRUE" : "FALSE");
        return list;
    }

    public String getProjectName() { return projectName; }
    public String getNeighbourhood() { return neighbourhood; }
    public long getOpeningDate() { return openingDate; }
    public long getClosingDate() { return closingDate; }
    public String getCreatedBy() { return createdBy; }
    public HashMap<FlatType, Integer> getUnits() { return units; }
    public HashMap<FlatType, Integer> getPrices() { return prices; }
    public ProjectTeam getProjectTeam() { return projectTeam; }
//    public void setUnits(HashMap<String, Integer> units) {
//        this.units = units;
//    }

    // method to check flat availability
    public void updateFlatAvailability(String flatType, int numberOfUnits) {
        this.units.put(FlatType.valueOf(flatType), numberOfUnits);
    }

    public void toggleProjectVisibility() {
        projectVisibility = !projectVisibility;
    }
    public boolean getProjectVisibility() {return projectVisibility;}

    public String toString() {
        return "Project{" +
                "projectName='" + projectName + '\'' +
                ", neighbourhood='" + neighbourhood + '\'' +
                ", openingDate=" + new Date(openingDate) +
                ", closingDate=" + new Date(closingDate) +
                ", createdBy='" + createdBy + '\'' +
                ", units=" + units +
                ", prices=" + prices +
                ", projectTeam=" + projectTeam +
                ", projectVisibility=" + projectVisibility +
                '}';
    }
}

