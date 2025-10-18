/* Logger Program
 Recieves logfile name from command line and will write to this file
 Will write the messages in the format of timestamp + [command] + message
 Stops logging when quit is received.
 */

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
public class Logger {
    private static PrintWriter logWriter;

    public static void main(String[] args) {

        // For when there is no log file name given 

        if (args.length != 1) {
            System.err.println("Missing logfileName");
            System.exit(1);
        }

        try {

            // Setting up user input and writing to file
            logWriter =  new PrintWriter(new FileWriter(args[0], true));
            Scanner scanner = new Scanner(System.in);

            // Logic for the formatting parts seperates the line into the command and message part and this is determined by the space. Also setting up the time variable which will show the timestamp
            // Lastly writes all this info to the file and have hardcoded the very first iteration when the program is first ran
            String input;
            input="START Logging Started. ";
            String Time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
            String[] parts = input.split(" ", 2);
            String command = parts[0];
            String message = (parts.length > 1) ? parts[1] : "";
            logWriter.println(Time + " [" + command + "] " + message);
            logWriter.flush();

            // After logging is started will continue logging until user exits the program.

            while (!(input = scanner.nextLine()).equals("QUIT")) {
               
            try {
            parts = input.split(" ", 2);
            command = parts[0];
            message = (parts.length > 1) ? parts[1] : "";
            logWriter.println(Time + " [" + command + "] " + message);
            logWriter.flush();
            } catch (Exception e) {
            logWriter.println("LOG ERROR: " + e.getMessage());
            logWriter.flush(); 
            }
            }
            
            // Once user wants to quit will write the final log update that shows the final entry
            
            input="STOP Logging Stopped. ";
            parts = input.split(" ", 2);
            command = parts[0];
            message = (parts.length > 1) ? parts[1] : "";
            logWriter.println(Time + " [" + command + "] " + message);
            logWriter.flush();
            logWriter.close();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}