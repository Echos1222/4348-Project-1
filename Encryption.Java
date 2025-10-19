import java.util.Scanner;
public class EncryptionProgram {
    private static String Password = ""; // Current passkey

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;
        while (!(input = scanner.nextLine()).equals("QUIT")) {
            input = input.trim(); 
             if (input.isEmpty()) continue;
        String[] inputs = input.split("\\s+", 2);
        String command = tokens[0].toUpperCase();

        switch (command) {
            case "PASSKEY":
                
            case "ENCRYPT":
                
            case "DECRYPT":
               
            default:
                System.out.println("ERROR Unknown command");
        } 

        }
    }
}