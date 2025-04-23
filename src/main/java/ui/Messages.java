package ui;

import static ui.Colours.*;

public class Messages {
    public static final String APPLICATION_NAME = "🏢 BTO Management System";

    public static final String BTO_ART = GREEN+"""
        ██████╗ ████████╗ ██████╗
        ██   ██╗╚══██╔══╝██╔═══██╗
        ██████╔╝   ██║   ██║   ██║
        ██   ██║   ██║   ██║   ██║
        ██████╔╝   ██║   ╚██████╔╝
        ╚═════╝    ╚═╝    ╚═════╝
    """+RESET;

    public static final String APPLICANT_MENU = CYAN+"""
    
            1) Add Filter        2) View Filters       3) Remove Filter
            4) Change Password   5) View Projects      6) Apply Project
            7) View Enquiries    8) Add Enquiry        9) Edit Enquiry
           10) Remove Enquiry    11)View BTO Applications
           12) Withdraw BTO Application
            0) Exit
           """+RESET;

    public static final String OFFICER_MENU = BRIGHT_BLUE+"""
            1) Add Filter        2) View Filters       3) Remove Filter
            4) Change Password   5) View Projects      6) Apply Project
            7) View Enquiries    8) Add Enquiry        9) Edit Enquiry
           10) Remove Enquiry
           11) View BTO Applications            12) Withdraw BTO Application
           13) Register as Officer to Project   14) View Registration Status
           15) Reply to Enquiry                 16) Change Application Status
            0) Exit
           """+RESET;

    public static final String MANAGER_MENU = RED+"""
            1) Add Filter        2) View Filters       3) Remove Filter
            4) Change Password   5) Create Project     6) Edit Project
            7) Delete Project    8) View All Projects  9) View My Projects
           10) Toggle Project Visibility       11) View Officer Registration
           12) Decide Officer Registration     13) Approve Applicant's Application 
           14) Reject Applicant's Application
           15) Approve Applicant's Withdrawal Application 
           16) Reject Applicant's Withdrawal Application
           17) Generate Applicant Report   18) View Enquiries  19) Reply to Enquiry
            0) Exit
           """+RESET;

    public static final String FILTER_MENU = """
            1) Neighbourhood
            2) Flat Type
            """;

    public static String printLine(){
        System.out.println(YELLOW + "-----------------------------------------------------------------------" + RESET);
        return "";
    }
}
