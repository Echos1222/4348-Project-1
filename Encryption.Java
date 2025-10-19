import java.util.Scanner;


public class Encryption{
    private static String password = "";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;

       
        while (!(input = scanner.nextLine()).equals("QUIT")) {
            Command(input);

        }
    }

    private static void Command(String input) {
        input = input.trim();                   
        if (input.isEmpty()) return;            
        
       
        String[] parts = input.split("\\s+", 2);
        String action = parts[0].toUpperCase();

        switch (action) {
            case "PASSKEY":
                setPassword(parts.length > 1 ? parts[1] : "");
                break;
            case "ENCRYPT":
                encrypt(parts.length > 1 ? parts[1] : "",password);
                break;
            case "DECRYPT":
                decrypt(parts.length > 1 ? parts[1] : "",password);
                break;
            default:
                System.out.println("ERROR Unknown command");
        }
    }

    
    private static void setPassword(String newPassword) {
        password = newPassword;
        System.out.println("RESULT "+newPassword);
    }

   
    private static void encrypt(String message,String key) {
        if (password.isEmpty()) {
            System.out.println("ERROR Passkey not set");
        } else {
         message = message.toUpperCase();
        key = key.toUpperCase();
        StringBuilder a = new StringBuilder();

    for (int i = 0; i < message.length(); i++) {
        char m = message.charAt(i);
        if (m < 'A' || m > 'Z') continue; // skips non-letters
        char k = key.charAt(i % key.length());

        int shift = (m - 'A' + (k - 'A')) % 26;
        a.append((char) ('A' + shift));
    }
    String encrypted= a.toString();    
         System.out.println("RESULT " + encrypted);
    }
    }
    public static void decrypt(String message,String key) {
        if (password.isEmpty()) {
            System.out.println("ERROR Passkey not set");
        } else {
            message = message.toUpperCase();
    key = key.toUpperCase();
    StringBuilder a = new StringBuilder();

    for (int i = 0; i < message.length(); i++) {
        char m = message.charAt(i);
        if (m < 'A' || m > 'Z') continue; // skips non-letters
        char k = key.charAt(i % key.length());

        int shift = (m - 'A' - (k - 'A') + 26) % 26;
        a.append((char) ('A' + shift));
    }
    String decrypted=a.toString();
            System.out.println("RESULT " + decrypted);
        }
    }

   
}