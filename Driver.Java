import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/* Interface for the project that accepts the command line with the log file name
 Will launch Encryption and Logger with the use of processes and communicate with it through I/O streams
 Contains the menu and is the hub for communication between all the programs 

 */
public class Driver {
    // How messages are sent to the other programs
    private static PrintWriter loggerInput;    
    private static PrintWriter encryptInput;
    
    // How messages are read from other programs
    private static Scanner loggerOutput;        
    private static Scanner encryptOutput;

    // Other program instances
    private static Process loggerProcess;       
    private static Process encryptProcess;

    //Global variable that stores the original command
    public static String Type="";     

    // history of previous passkeys
    private static List<String> history = new ArrayList<>(); 

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java Driver <logFileName>");
            System.exit(1);
        }

        try {
            // Starts both processes
            initializeProcesses(args[0]);
            
            // Opens the user menu
            Menu();
            
            // Terminates the processes and the entire program
            loggerInput.println("QUIT");
            loggerInput.flush();
            encryptInput.println("QUIT");
            encryptInput.flush();
            try {
            loggerProcess.waitFor();
            encryptProcess.waitFor();
            } catch (Exception e) {
            e.printStackTrace();
        }
        loggerInput.close();
        encryptInput.close();
        loggerOutput.close();
        encryptOutput.close();         
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method takes in log file name as an argument and will initialize anything involved in other programs and communication between the programs

    private static void initializeProcesses(String logFileName) throws IOException {
        loggerProcess = new ProcessBuilder("java", "Logger", logFileName).start();
        encryptProcess = new ProcessBuilder("java", "Encryption").start();
        
        
        loggerInput = new PrintWriter(loggerProcess.getOutputStream());
        encryptInput = new PrintWriter(encryptProcess.getOutputStream());


        loggerOutput = new Scanner(loggerProcess.getInputStream());
        encryptOutput = new Scanner(encryptProcess.getInputStream());

        // First instance of logger just to start the logging

        loggerInput.println("QUIT");
        loggerInput.flush();

        loggerInput.println(logFileName);
        loggerInput.flush();
    }


    // Main user menu that lets users choose what command they want to do.

    private static void Menu(){
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            System.out.print("Enter command: ");
            input = scanner.nextLine();
            loggerInput.println("User " + input);
            loggerInput.flush();

            

            switch (input.toUpperCase()) {
                case "PASSWORD" :
                Type= "PASSWORD ";
                    Password(scanner);
                    break;
                    
                case "ENCRYPT" :
                 Type= "ENCRYPT ";
                    EncryptDecrypt(scanner);
                    break;

                case "DECRYPT" :
                 Type= "DECRYPT ";
                    EncryptDecrypt(scanner);
                    break;

                case "HISTORY" :
                    
                    if (history.isEmpty()) {
                        System.out.println("History is Empty");
                        return;
                    }
                    else { 
                        System.out.println("History");
                        for (int i = 0; i < history.size(); i++) {
                            System.out.println("[" + i + "]" + " - " + history.get(i));
                        }       
                    }
                    break;
                case "QUIT" :
                     loggerInput.println("STOP Driver Exiting");
                    loggerInput.flush();
                    scanner.close();
                    return;
                default:
                    System.out.println("ERROR Unknown command");
                    loggerInput.println("ERROR Unkown command: " + input);
                    loggerInput.flush();
            }

            if (input.equals("QUIT")) {
                break;
            }
        }
    }

     private static void Password(Scanner scanner) {
        System.out.print("Use history (H) or new password(N)? ");
        String input = scanner.nextLine().trim().toUpperCase();
        
        String password;

        // Three branches first two are either choosing history or a new password.

        if (input.equals("H") && !history.isEmpty()) {
            password = selectFromHistory(scanner); 
            if (password.isEmpty()) return;
        } else if (input.equals("N")) {
            System.out.print("Enter new password (letters only): ");
            password = scanner.nextLine().trim().toUpperCase();
        } else {
            System.out.println("ERROR invalid choice or empty history.");
            loggerInput.println("ERROR Invalid Password Choice.");
            loggerInput.flush();
            return;
        }

       // Error check to make sure passwords are letters only

        if (!password.matches("[A-Z]+")) {
            System.out.println("ERROR Password Must Contain Only Letters.");
            loggerInput.println("ERROR Invalid Password Input.");
            loggerInput.flush();
            return;
        }
        
        // Since we might have to type something new for history we need to remember our old command

        String command= Type+password;
        if (!command.trim().isEmpty()) {
            encryptInput.println(command);
            encryptInput.flush();

            // log the encrypt or decrypt password

            String response= encryptOutput.nextLine();
            loggerInput.println(response);
            loggerInput.flush();
            System.out.println(response);
        }

    }
    private static void EncryptDecrypt(Scanner scanner) {
        System.out.print("Use history (H) or new text(N)? ");
        String input = scanner.nextLine().trim().toUpperCase();

        String text="";
        if (input.equals("H") && !history.isEmpty()) {
            text = History(scanner);
            if (text.isEmpty()) return;
        } else if (input.equals("N")) {
            System.out.print("Enter text (letters only): ");
            text = scanner.nextLine().trim().toUpperCase();
            history.add(text);
        } else {
            System.out.println("ERROR invalid choice or empty history.");
            loggerInput.println("ERROR Invalid Text Choice.");
            loggerInput.flush();
            return;
        }

        // error check to make sure user inputs valid passkey

        if (!text.matches("[A-Z]+")) {
            System.out.println("ERROR Text Must Contain Only Letters.");
            loggerInput.println("ERROR Invalid Text Choice.");
            loggerInput.flush();
            return;
        }
        
        // First half is for encrypting to decrypting second half half is to log what was done.

        String command= Type+text;
        if (!command.trim().isEmpty()) {
            encryptInput.println(command);
            encryptInput.flush();
            loggerInput.println(command);
            loggerInput.flush();
        }

        // allows time for the other programs to finish

        try {
            
            Thread.sleep(100);
        } catch (InterruptedException ignored) {}
        
       
        // reads result from encryption program
        String response = "";
        while (encryptOutput.hasNextLine()) {
            response = encryptOutput.nextLine().trim();
            if (!response.isEmpty()) break; 
        }

        if (!response.isEmpty()) {
            System.out.println(response);
             loggerInput.println(response);
            loggerInput.flush();
            
            
            if (response.startsWith("RESULT ")) {
                 response = response.substring(7).trim();
                history.add(response);
            }
        }
    }

   
    private static String History(Scanner scanner) {
        System.out.println("Select from history:");

        // Shows all previous passwords

        for (int i = 0; i < history.size(); i++) {
            System.out.println("[" + i + "]" + " - " + history.get(i));
        }

        System.out.print("Enter number or 'N'");
        String input = scanner.nextLine().trim().toUpperCase();

        // If new ignores the list otherwise chooses the word from the history

        if(input.equals("N")) return "";
        try {
            int index = Integer.parseInt(input);
            if (index >= 0 && index < history.size()) {
                return history.get(index);
            }
        } catch (NumberFormatException ignored) { }
        
        System.out.println("ERROR Invalid selection.");
        return "";
    }

 
    



}