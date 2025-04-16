package ui;

import static ui.Colours.*;

public class Messages {
    public static final String APPLICATION_NAME = "🏢 BTO Management System";

    public static final String BTO_ART = """
        ██████╗ ████████╗ ██████╗ 
        ██   ██╗╚══██╔══╝██╔═══██╗
        ██████╔╝   ██║   ██║   ██║
        ██   ██║   ██║   ██║   ██║
        ██████╔╝   ██║   ╚██████╔╝
        ╚═════╝    ╚═╝    ╚═════╝ 
""";

    public static final String APPLICANT_MENU = """
            1) Add Filter        2) View Filters       3) Remove Filter
            4) Change Password   5) View Projects      6) Apply Project
            7) View Enquiries    8) Add Enquiry        9) Edit Enquiry
           10) Remove Enquiry    0) Exit
            """;

    public static final String OFFICER_MENU = """
            1) Add Filter        2) View Filters       3) Remove Filter
            4) Change Password   5) View Projects      6) Apply Project
            7) View Enquiries    8) Add Enquiry        9) Edit Enquiry
           10) Remove Enquiry   11) Register as Officer to Project
           12) View Registration Status
           13) Reply to Enquiry 14) Change Application Status
            0) Exit
            """;

    public static final String MANAGER_MENU = """
            1) Add Filter        2) View Filters       3) Remove Filter
            4) Change Password   5) Create Project     6) Edit Project
            7) Delete Project    8) View All Projects  9) View My Projects
           10) Toggle Project Visibility       11) View Officer Registration
           12) Decide Officer Registration    13) Approve Applicant's Application 
           14) Reject Applicant's Application
           15) Approve Applicant's Withdrawal Application 
           16) Reject Applicant's Withdrawal Application
           17) Generate Applicant Report   18) View Enquiries  19) Reply to Enquiry
            0) Exit
            """;

    public static final String FILTER_MENU = """
            1) Neighbourhood
            2) Flat Type
            """;

    public static String printLine(){
        System.out.println(YELLOW + "-----------------------------------------------------------------------" + RESET);
        return "";
    }
}
