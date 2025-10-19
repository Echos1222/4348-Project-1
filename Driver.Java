import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Driver {
    private static Process loggerProcess;       
    private static Process encryptionProcess;   
    private static PrintWriter loggerInput;    
    private static PrintWriter encryptionInput; 
    private static Scanner loggerOutput;        
    private static Scanner encryptionOutput;  

    private static List<String> hist = new ArrayList<>(); // Session history

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java Driver <logFileName>");
            System.exit(1);
        }

        try {
            initializeProcesses(args[0]);   
            Menu();             
            terminate();           
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initializeProcesses(String logFileName) throws IOException {
        loggerProcess = new ProcessBuilder("java", "Logger", logFileName).start();
        encryptionProcess = new ProcessBuilder("java", "Encryption").start();
        
        
        loggerInput = new PrintWriter(loggerProcess.getOutputStream());
        encryptionInput = new PrintWriter(encryptionProcess.getOutputStream());


        loggerOutput = new Scanner(loggerProcess.getInputStream());
        encryptionOutput = new Scanner(encryptionProcess.getInputStream());

        // log start in Logger
        loggerInput.println("QUIT");
        loggerInput.flush();

        
        loggerInput.println(logFileName);
        loggerInput.flush();
    }

}