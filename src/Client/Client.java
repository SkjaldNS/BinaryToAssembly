
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        int port = 9424;
        String host = "localhost";
        try {
            Socket s = new Socket(host, port);
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            Scanner scanner = new Scanner(System.in);
            while(true) {
                System.out.println("Enter 1 for binary instruct, enter 2 to load from a file: ");
                try {
                    int choice = scanner.nextInt();
                    if (choice == 1) {
                        System.out.println("Enter binary instruction: ");
                        String binary = scanner.next();
                        if(!Binary.validateBinary(binary)) {
                            System.out.println("Invalid binary string. Please enter a valid 16 digit binary string.");
                            continue;
                        }
                        PrintWriter output = new PrintWriter(s.getOutputStream(), true);
                        output.println(binary);
                        //int read = 0;
                        //ObjectInputStream in = new ObjectInputStream(s.getInputStream());
                        //while((read = s.getInputStream().read()) > -1) {
                        //    String message = (String) in.readObject();
                        //    System.out.println("Message: " + message);
                        //}
                        //in.close();
                        out.close();
                    } else if (choice == 2) {
                        System.out.println("Enter filename: ");
                        String filename = scanner.next();
                        String[] data = FileUtils.loadFile(filename);
                        if(!Binary.validateBinary(data)) {
                            System.out.println("File contains invalid binary string. Please ensure that each line contains a valid 16 digit binary string.");
                            continue;
                        }
                        ObjectInputStream in = new ObjectInputStream(s.getInputStream());
                        String response = "";
                        for (String line : data) {
                            out.writeObject(line);
                            int read;
                            while((read = s.getInputStream().read()) > -1) {
                                response += ((String) in.readObject()) + "\n";
                            }
                        }
                        in.close();
                        out.close();
                        System.out.println(response);
                        System.out.println("Enter filename to save: ");
                        filename = scanner.next();
                        FileUtils.saveFile(filename, response.split("\n"));
                    } else {
                        System.out.println("Invalid choice");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input");
                    scanner.next();
                }
            }
        } catch (Exception e) {
            System.out.println("Error connecting to server");
        }
    }
}
