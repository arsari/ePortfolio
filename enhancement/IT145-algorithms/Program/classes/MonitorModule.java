package classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * This is third of the four principal classes of the monitoring system
 * program, MonitorModule. This class read the animal.txt file and the
 * habitats.txt to display their content base on the user selection of
 * monitoring, animal or habitat, and the details and options on each
 * selection.
 *
 * @author	Arturo Santiago-Rivera <i>asantiago@arsari.com</i>
 * @course	IT145 Foundation in Application Development 18EW5
 * @college	Southern New Hampshire University
 */
public class MonitorModule {
    
    private static final String ANIMALS_FILE = "animals.txt";
    private static final String HABITATS_FILE = "habitats.txt";

    /**
     * Method to display monitoring dashboard
     * 
     * @param  userRole User system role
     * @param  user     User complete name
     * @param  path     Directory path were txt file is located
     * @param  monitor  Monitor option, animal or habitat
     * @return exit     Return true or false for program exit
     * @throws java.io.FileNotFoundException
     */
    public static boolean showDashboard(String userRole, String user, String path, String monitor) throws FileNotFoundException, Exception {
        Scanner scnr = new Scanner(System.in); // init scanner for input
        String fname;
        char i = 64;
        char select = 0;
        String detailOf;
        boolean exit = false;
        
        // define type of monitoring
        if ("animals".equals(monitor)) {
            fname = ANIMALS_FILE;
        } else {
            fname = HABITATS_FILE;
        }
        
        // display standard monitoring screen and selction detail to minitor
        while (select != 'z') {
            Display.showBanner(user, userRole); // display authorized user logged

            System.out.println("[ " + monitor.toUpperCase() + " MONITORING DASHBOARD ]\n");

            // open monitoring file
            File file = new File(path + fname);
            Scanner fileContent = new Scanner(file);

             // read file content
            while (fileContent.hasNextLine()) { // stop while loop at final line
                String line = fileContent.nextLine(); // iterate each line on file

                if (line.contains("Details")) {
                    i += 1;
                    System.out.println("[" + i + "] " + line);
                }
            }

            System.out.println("\n[z] Return to Main Dashboard");

            System.out.printf("\n\nSelect %s to monitor: ", fname.substring(0, fname.length() - 5).toUpperCase());
            select = scnr.next().charAt(0);
            
            switch (select) {
                case 'z':
                    exit = RoleModule.showDashboard(userRole, user, path);
                    break;
                case 'a':
                    if (fname.equals("animals.txt")) {
                        detailOf = "Animal - Lion";
                        showDetails(fname, detailOf, user, userRole, path);
                    } else {
                        detailOf = "Habitat - Penguin";
                        showDetails(fname, detailOf, user, userRole, path);
                    }
                    break;
                case 'b':
                    if (fname.equals("animals.txt")) {
                        detailOf = "Animal - Tiger";
                        showDetails(fname, detailOf, user, userRole, path);
                    } else {
                        detailOf = "Habitat - Bird";
                        showDetails(fname, detailOf, user, userRole, path);
                    }
                    break;
                case 'c':
                    if (fname.equals("animals.txt")) {
                        detailOf = "Animal - Bear";
                        showDetails(fname, detailOf, user, userRole, path);
                    } else {
                        detailOf = "Habitat - Aquarium";
                        showDetails(fname, detailOf, user, userRole, path);
                    }
                    break;
                case 'd':
                    if (fname.equals("animals.txt")) {
                        detailOf = "Animal - Giraffe";
                        showDetails(fname, detailOf, user, userRole, path);
                    }
                    break;
            }
            i = 64;
        }
        return exit;
    }
    
    /**
     * Method to display selection details
     * 
     * @param fname    Name of the file to open/read animals or habitats
     * @param detailOf Display the details of the selected option 
     * @param userName User full name
     * @param userRole User system role
     * @param path     Directory path were txt file is located
     * @throws java.io.FileNotFoundException
     * @throws java.lang.InterruptedException
     */
    private static void showDetails(String fname, String detailOf, String user, String userRole, String path) throws FileNotFoundException, IOException, InterruptedException {
        Display.showBanner(user, userRole); // display authorized user logged
        
        String separator = "+" + Display.strRepeat("-", 54) + "+";
        
        System.out.println(separator);
        System.out.printf("| Details for: %-39s |\n", detailOf);
        
        // open monitoring file
        File file = new File(path + fname);
        Scanner fileContent = new Scanner(file);

        // read file content
        while (fileContent.hasNextLine()) {
            String line = fileContent.nextLine();
            if (line.equals(detailOf)){
                while (!line.equals("")) {
                    line = fileContent.nextLine();
                    if (line.equals("") || !fileContent.hasNextLine()) {
                        break;
                    } else {
                        String[] s = line.split(": "); // split line content as array
                        System.out.println(separator);
                        if (line.contains("*****")) {
                            String message = s[0].substring(5) + ": " + s[1];
                            Display.showDialog(message); // call display dialog box method
                            System.out.printf("|\033[1;33;41m %-18s \033[0m|\033[1;33;41m %-31s \033[0m|\n", s[0].substring(5), s[1]);
                        } else {
                            System.out.printf("| %-18s | %-31s |\n", s[0], s[1]);
                        }
                    }
               }
            }
        }
        System.out.println(separator);
        System.out.print("\n\nPress ENTER to return to Dashboard...");
        System.in.read(); // wait user press enter key
    }
}
