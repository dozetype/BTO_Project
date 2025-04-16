package storage;

public class Enquiry {
    private final String ID;
    private final String askerID; //name of asker
    private final String projectName;
    private String question; //the question itself
    private String reply;

    public Enquiry(String ID, String userID, String projectName, String question, String reply) {
        this.ID = ID;
        this.askerID = userID;
        this.projectName = projectName;
        this.question = question;
        this.reply = reply;
    }
    public String getID() {
        return ID;
    }
    public String getAskerID(){
        return askerID;
    }
    public String getProjectName(){ return projectName; }
    public String getQuestion(){
        return question;
    }
    public String getReply(){
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
    public void setQuestion(String question) { this.question = question; }

    public String toString() {
        return "Enquiry{" +
                "ID='" + ID + '\'' +
                ", asker ID='" + askerID + '\'' +
                ", projectName='" + projectName + '\'' +
                ", question='" + question + '\'' +
                ", reply='" + reply + '\'' +
                // Add other fields you want to display
                '}';
    }
}
