package classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * This is fourth of the four principal classes of the monitoring system
 * program, UsersModule. If the user is an administrator odf the system,
 * this class read the credentials.txt file and display a table with the
 * authorizaed users in the system and their modules permissions to access 
 * in the system.
 *
 * @author	Arturo Santiago-Rivera <i>asantiago@arsari.com</i>
 * @course	IT145 Foundation in Application Development 18EW5
 * @college	Southern New Hampshire University
 */
public class UsersModule {
    
    /**
     * Method to display user name and user role table.
     * 
     * @param  userRole User system role
     * @param  user     User complete name
     * @param  path     Directory path were txt file is located
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public static void showMngrTable(String userRole, String user, String path) throws FileNotFoundException, IOException, InterruptedException {
        String a = null;
        String h = null;
        String u = null;
        
        Display.showBanner(user, userRole); // display authorized user logged
        
        System.out.println("[ USERS MANAGER TABLE ]");
        
        // read user credentials file
        File file = new File(path + "credentials.txt");
        Scanner fileContent = new Scanner(file);
        
        // construct and display user list table
        System.out.println(Display.strRepeat(" ", 42) + "+" + Display.strRepeat("-", 28) + "+");
        System.out.printf(Display.strRepeat(" ", 42) + "|      %-21s |\n", "DASHBOARD ACCESS");
        System.out.println(Display.strRepeat(" ", 5) + "+" + Display.strRepeat("-", 65) + "+");
        System.out.printf(Display.strRepeat(" ", 5) + "| %-18s | %-13s | %7s | %8s | %5s |\n", "NAME", "ROLE", "Animals", "Habitats", "Users");

        int i = 1;
        while (fileContent.hasNextLine()) { // stop while loop at final line
            String line = fileContent.nextLine(); // iterate each line on file
            String[] s = line.split(" "); // split line content as array
            
            switch (s[s.length - 1]) {
                case "veterinarian":
                    a = "";
                    h = "X";
                    u = "";
                    break;
                case "zookeeper":
                    a = "X";
                    h = "X";
                    u = "";
                    break;
                case "admin":
                    a = "X";
                    h = "X";
                    u = "X";
                    break;
            }
            System.out.println("+" + Display.strRepeat("-", 70) + "+");
            System.out.printf("| %2d | %-18s | %-13s |    %-4s |     %-4s |   %-3s |\n", i, s[0], s[s.length - 1], a, h, u);
            ++i;
        }
        System.out.println("+" + Display.strRepeat("-", 70) + "+");
        System.out.print("\n\033[1;33m *** Sorry, add/edit options are on development *** \033[0m");
    }
}
