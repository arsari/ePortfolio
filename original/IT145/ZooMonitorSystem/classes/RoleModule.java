package classes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class RoleModule {

    /**
     * Method display user role dashboard.
     * 
     * @param  userRole User system role
     * @param  user     User full name
     * @param  path     Directory path were txt file is located
     * @return exitApp  Return true or false to exit program
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     * @throws Exception
     */
    public static boolean showDashboard(String userRole, String user, String path) throws IOException, InterruptedException, Exception {
        String line;
        boolean exit;

        Display.showBanner(user, userRole); // display logged authorized user
        
        System.out.println("[ MAIN DASHBOARD ]\n");

        // open user role file
        File file = new File(path + userRole + ".txt");
        Scanner fileContent = new Scanner(file);

        // read file content
        while (fileContent.hasNextLine()) { // stop while loop at final line
            line = fileContent.nextLine(); // iterate each line on file
            
            String[] sentence = wrapLine(line, 70); // call wrap method
            
            for (int i = 0; i < sentence.length; ++i) {
                System.out.println(sentence[i]);
            }
        }

        exit = Display.showMenu(userRole, user, path);
        
        return exit;
    }
    
    /**
     * This method takes a string value and a line length, and returns
     * an array of lines.
     * <p>
     * Lines are cut on word boundaries, where the word
     * boundary is a space character. Spaces are included as the last character
     * of a word, so most lines will actually end with a space. This isn't too
     * problematic, but will cause a word to wrap if that space pushes it past
     * the max line length.
     * 
     * @author   Robert Hanson (Programmer's Cookbook)
     * @link     http://progcookbook.blogspot.com/2006/02/text-wrapping-function-for-java.html
     * @modified Arturo Santiago-Rivera (3 june 2018)
     * @param    text String to wrap
     * @param    len  Index where string will be wrapped
     * @return   ret  Wrapped string line
     */
    public static String[] wrapLine(String text, int len) {
        // return empty array for null text
        if (text == null)
            return new String[] {};

        // return text if len is zero or less
        if (len <= 0)
            return new String[] {text};

        // return text if less than length
        if (text.length() <= len)
            return new String[] {text};

        char[] chars = text.toCharArray();
        ArrayList<String> lines = new ArrayList<>();
        StringBuilder line = new StringBuilder();
        StringBuilder word = new StringBuilder();

        for (int i = 0; i < chars.length; i++) {
            word.append(chars[i]);

            if (chars[i] == ' ') {
                if ((line.length() + word.length()) > len) {
                    lines.add(line.toString());
                    line.delete(0, line.length());
                }

                line.append(word);
                word.delete(0, word.length());
            }
        }

        // handle any extra chars in current word
        if (word.length() > 0) {
            if ((line.length() + word.length()) > len) {
              lines.add(line.toString());
              line.delete(0, line.length());
            }
            line.append(word);
        }

        // handle extra line
        if (line.length() > 0) {
            lines.add(line.toString());
        }

        String[] ret = new String[lines.size()];
        for (int c = 0; c < lines.size(); ++c) {
            ret[c] = (String) lines.get(c);
        }
        
        return ret;
    }
}
