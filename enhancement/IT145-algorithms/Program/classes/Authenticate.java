package classes;

import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * This is first of the four principal classes of the monitoring system
 * program, Authentice Module. This class read the credentials.txt file
 * and validate the users input with the data in the file to authenticate
 * and authorize the user into the system.
 *
 * @author	Arturo Santiago-Rivera <i>asantiago@arsari.com</i>
 * @course	IT145 Foundation in Application Development 18EW5
 * @college	Southern New Hampshire University
 */
public class Authenticate {
    
    private static final String CREDENTIALS_FILE = "credentials.txt";

    /**
     * Method to authenticate user in computer system.
     *
     * @param  user User complete name to authenticate
     * @param  path Directory were txt files are located
     * @return exit Return false if user not in system, true if fail password 3 times
     * @throws java.io.FileNotFoundException
     * @throws Exception
     */
    public static boolean userCredentials(String user, String path) throws FileNotFoundException, Exception {
        Scanner scnr = new Scanner(System.in); // init scanner for input
        String userPass;
        String userRole;
        boolean exit;

        // read user credentials file
        File file = new File(path + CREDENTIALS_FILE);
        Scanner fileContent = new Scanner(file);

        while (fileContent.hasNextLine()) { // stop while loop at final line
            String line = fileContent.nextLine(); // iterate each line on file
            String[] s = line.split("\\s+"); // split line content as array on whitespaces

            // verified user input credeentials and authorized system access
            if (s[0].equals(user)) {
                int i;
                for (i = 0; i < 3; ++i) { // password fail loop
                    if (path.contains("src")) {
                        System.out.print("\nEnter password (CaseSensitive): ");
                        userPass = scnr.nextLine();
                    } else {
                        Console console = System.console();
                        char[] pass = console.readPassword("\nEnter Password (CaseSensitive): ", "*"); // hide password on console/shell
                        userPass = String.valueOf(pass);
                    }
                    
                    String encrypPass = MD5Digest(userPass); // call digest class method
                    
                    if (s[1].equals(encrypPass)) {
                        userRole =  s[s.length-1];
                        exit = RoleModule.showDashboard(userRole, user, path); // call role class method
                        return exit;
                    } else if (i == 2) {
                        Display.clearScreen();
                        String message = "Account locked. Program terminated";
                        Display.showDialog(message); // call display dialog box
                        return true;
                    } else {
                        System.out.printf("\n\033[1;33;41m ERROR: Password is incorrect (%d) \033[0m\n", i + 1);
                    }
                }
            }
        }
        return false;
    }
    
     /**
     * Method to digest user password
     *
     * @param  userPass   User password to convert to bytes
     * @return digestPass Return string to compare with password on file
     * @throws java.security.NoSuchAlgorithmException
     */
    private static String MD5Digest(String userPass) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(userPass.getBytes());
        byte[] digest = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
        }

        String digestPass = sb.toString();

        return digestPass;
    }
}
