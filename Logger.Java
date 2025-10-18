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
            logWriter =  new PrintWriter(new FileWriter(args[0], true));
            
            Scanner scanner = new Scanner(System.in);

            String input;

           
            while (!(input = scanner.nextLine()).equals("QUIT")) {
                
                try {
           
            String Time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());

           
            String[] parts = input.split(" ", 2);
            String command = parts[0];
            String message = (parts.length > 1) ? parts[1] : "";

            
            logWriter.println(Time + " [" + command + "] " + message);
            logWriter.flush();
            } catch (Exception e) {
            logWriter.println("LOG ERROR: " + e.getMessage());
            logWriter.flush(); 
            }
            }
            logWriter.println("Done");
            logWriter.close();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}