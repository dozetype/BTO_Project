package ui;

public interface IUi {
    /**
     * Used for inputting Strings, will help to catch any exceptions
     * @return The user String input
     */
    public String inputString();

    /**
     * Used for inputting integer, will help to catch any exceptions
     * @return The user integer input
     */
    public int inputInt();

    /**
     * Output message and get input
     * @return The user String input
     */
    public String readUserID();

    /**
     * Output message and get input
     * @return The user String input
     */
    public String readPassword();


    /**
     * Output message and get input
     * @return The user String input
     */
    public String switchOff();
}
