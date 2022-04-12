
import classes.Authenticate;
import classes.Display;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import org.fusesource.jansi.AnsiConsole;

/**
 * Representation of a monitoring system for a zoo composed of a module to
 * authenticate and authorize user.
 * <p>
 * There are many aspects of a zoo that need to be in place to keep it running.
 * Two of those aspects are controlling data access and monitoring animal
 * activities in exhibits.
 *
 * @title Module Seven: 7-1 Final Project Submission: Authentication or
 * Monitoring System
 * @author Arturo Santiago-Rivera <i>arturo.santiago-rivera@snhu.edu</i>
 * @profesor John Walton
 * @course IT-145-X5942 Found in App Development 18EW5
 * @university Southern New Hampshire University
 * @version 0.1.180616 16 June 2018
 */
public class ZooMonitorSystem {

    /**
     * Main executable class
     *
     * @param args The command line prompt argument
     * @throws java.io.IOException if display class not valid
     * @throws java.lang.InterruptedException if Display class not valid
     * @throws Exception if Authenticate class null
     */
    public static void main(String[] args) throws IOException, InterruptedException, Exception {
        Scanner scnr = new Scanner(System.in); // input init scanner
        String dirPath;
        boolean exit = false;

        // verification of run mode, NetBeans or OS terminal
        File dir = new File("src/txt_files");
        if (!dir.exists()) {
            dirPath = "txt_files/"; // cmd or terminal shell path//
        } else {
            dirPath = "src/txt_files/"; // NetBeans debug path
        }

        AnsiConsole.systemInstall(); // init ansi code color for any os
        Display.clearScreen(); // call display clear shell screen
        
        System.out.println("Login\n" + Display.strRepeat("-", 5));

        // obtain user first name or exit program
        while (!exit) {
            System.out.print("Enter first name (z to exit): ");
            String userFirstName = scnr.nextLine().toLowerCase();

            if ("z".equals(userFirstName)) {
                exit = true;
            } else {
                System.out.print("\nEnter last name: ");
                String userLastName = scnr.nextLine().toLowerCase();

                String user = userFirstName + "." + userLastName;
                
                exit = Authenticate.userCredentials(user, dirPath); // call authenticate class method

                if (!exit) {
                    System.out.println("\n\033[1;33;41m ERROR: User " + user.toUpperCase() + " not found. \033[0m\n");
                }
            }
        }
        System.out.println("\n\033[1;33;42m " + Display.strRepeat("<", 10) + " Good Bye! " + Display.strRepeat(">", 10) + " \033[0m\n");

        // close scanner and ansiconsole, exit dialog system
        scnr.close();
        AnsiConsole.systemUninstall();
        System.exit(0);
    }
}
