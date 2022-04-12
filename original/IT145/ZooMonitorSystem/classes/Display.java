package classes;

import java.awt.Toolkit;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Display {
    
    // get computer OS type
    private static final String OS_SYS = System.getProperty("os.name").toLowerCase();
    
    /**
     * Method to clear console/shell screen and print program header
     *
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public static void clearScreen() throws IOException, InterruptedException {
        // verified os tyoe
        if (OS_SYS.contains("win")) {
           new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); // clear shell screen in windows   
        } else {
           new ProcessBuilder("clear").inheritIO().start().waitFor(); // clear shell screen in macOS
        }
        
        // clear shell on non-standard win, macOS bash
        System.out.print("\033[2J\033[H");

        // display screen header
        String tb = "\033[1;33;44m" + strRepeat("*", 73) + "\033[0m";
        String spcr = "\033[1;33;44m*" + strRepeat(" ", 71) + "*\033[0m";
        
        System.out.println("\n" + tb + "\n" + spcr);
        System.out.println("\033[1;33;44m*" + strRepeat(" ", 32) + "Welcome" + strRepeat(" ", 32) + "*\033[0m");
        System.out.println("\033[1;33;44m*"+ strRepeat(" ", 35) + "to" + strRepeat(" ", 34) + "*\033[0m");
        System.out.println("\033[1;33;44m*" + strRepeat(" ", 25) + "Zoo Monitoring System" + strRepeat(" ", 25) + "*\033[0m");
        System.out.println(spcr + "\n" + tb + "\n");
    }

    /**
     * Method to repeat string times time.
     *
     * @param str   string to repeat
     * @param times repeat string times time
     * @return      generated string
     */
    public static String strRepeat(String str, int times) {
        return new String(new char[times]).replace("\0", str);
    }
    
    /**
     * Method to display banner with authorized user information
     *
     * @param  userName User complete name
     * @param  userRole User system role
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public static void showBanner(String userName, String userRole) throws IOException, InterruptedException {
        String banner = "|\033[1;37;45m Logged in as: " + userName + "  |  System Role: " + userRole + " \033[0m|";
        String separator = "+" + strRepeat("-", (banner.length() - 16)) + "+";

        clearScreen();
        System.out.println(separator + "\n" + banner + "\n" + separator + "\n");
    }
    
    /**
     * Method to display warning dialog box with sound
     *
     * @param message System alert message
     * @throws java.io.IOException
     */
    public static void showDialog(String message) throws IOException {
        // verified os type
        if (OS_SYS.contains("win")) {
            final Runnable sound = (Runnable)Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.default");
            sound.run();
        } else {
            Toolkit.getDefaultToolkit().beep();
        }

        // construct and display dialog box
        JOptionPane pane = new JOptionPane(message, JOptionPane.WARNING_MESSAGE); 
        JDialog dialog = pane.createDialog("Monitor System Alert"); 
        dialog.setAlwaysOnTop(true); 
        dialog.setVisible(true);
        dialog.setDefaultCloseOperation(JOptionPane.OK_OPTION);
    }
    
    /**
     * Method to display main menu
     * 
     * @param  userRole User system role
     * @param  user User complete name to authenticate
     * @param  path Directory were txt files are located
     * @return exit Return true to exit software
     * @throws java.io.IOException
     */
    public static boolean showMenu(String userRole, String user, String path) throws Exception {
        Scanner scnr = new Scanner(System.in); // init scanner input
        String monitor;
        boolean exit = false;
        
        // display main menu per role
        String menuOptions = "[z] Log out";
        
        switch (userRole) {
            case "veterinarian":
                menuOptions += "  [a] Monitor Animal";
                break;
            case "zookeeper":
                menuOptions += "  [a] Monitor Animal  [h] Monitor Habitat";
                break;
            default:
                menuOptions += "  [a] Monitor Animal  [h] Monitor Habitat  [u] Users Manager";
                break;
        }
        
        // main menu option selection
        while (!exit) {
            System.out.print(strRepeat("\n", 3));
            System.out.println("--Main Menu" + strRepeat("-", (menuOptions.length() - 11)));
            System.out.println(menuOptions);
            
            char option = scnr.next().charAt(0);
            
            switch (option) {
                case 'z':
                    clearScreen();
                    String message = "You log out succesfully";
                    showDialog(message); // call dialog box
                    exit = true;
                    break;
                default:
                    exit = RoleModule.showDashboard(userRole, user, path);
            }
        }
        return exit;
    }
}
