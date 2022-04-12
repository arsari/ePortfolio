
import classes.Authenticate;
import classes.Display;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import org.fusesource.jansi.AnsiConsole;

/**
 * Representation of a monitoring system for a zookeeper composed of a module to
 * authenticate and authorize user, a module for monitor animals or habitats,
 * and a module for users management.
 *
 * Once the users gain entry into the program, they only should see data related 
 * to their particular role. The program read the information in a file with the 
 * authorized users and compare it with the user input credentials. The user input 
 * their system username and up to three times their system password. When the user 
 * is authenticated, the program read the corresponding user role file that describes 
 * the particular duties activities authorized in the monitor system.
 *
 * This is the main class and naming to run the program.
 *
 * @author	Arturo Santiago-Rivera <i>asantiago@arsari.com</i>
 * @course	IT145 Foundation in Application Development 18EW5
 * @college	Southern New Hampshire University
 */
public class ZooMonitorSystem {

  /**
   * Main executable class
   * 
   * Initieted the program and the setup of the data files location 
   * and OS identification. Follow with the user authetication and 
   * authorization to access the monitor system.
   *
   * @param args The command line prompt argument
   * @throws java.io.IOException            if display class not valid
   * @throws java.lang.InterruptedException if Display class not valid
   * @throws Exception                      if Authenticate class null
   */
  public static void main(String[] args) throws IOException, InterruptedException, Exception {
    Scanner scnr = new Scanner(System.in); // input init scanner
    String dirPath;
    boolean exit = false;

    // verification of run mode, NetBeans or OS terminal
    File dir = new File("txt_files/");
    if (!dir.exists()) {
      dirPath = "src/txt_files/"; // cmd or terminal shell path//
    } else {
      dirPath = "txt_files/"; // NetBeans debug path
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
    System.out.println(
        "\n\033[1;33;42m " + Display.strRepeat("<", 10) + " Good Bye! " + Display.strRepeat(">", 10) + " \033[0m\n");

    // close scanner and ansiconsole, exit dialog system
    scnr.close();
    AnsiConsole.systemUninstall();
    System.exit(0);
  }
}
