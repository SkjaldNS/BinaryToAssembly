import java.util.Scanner;
import java.lang.*;

public class Main {
    public static void main(String[] args) {
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
                    System.out.println(Binary.convertBinaryToAssembly(binary));
                } else if (choice == 2) {
                    System.out.println("Enter filename: ");
                    String filename = scanner.next();
                    String[] data = FileUtils.loadFile(filename);
                    if(!Binary.validateBinary(data)) {
                        System.out.println("File contains invalid binary string. Please ensure that each line contains a valid 16 digit binary string.");
                        continue;
                    }
                    String resultSet = "";
                    for (String line : data) {
                        resultSet += Binary.convertBinaryToAssembly(line) + "\n";
                        System.out.println(Binary.convertBinaryToAssembly(line));
                    }
                    System.out.println("Enter filename to save: ");
                    filename = scanner.next();
                    FileUtils.saveFile(filename, resultSet.split("\n"));
                } else {
                    System.out.println("Invalid choice");
                }
            } catch (Exception e) {
                System.out.println("Invalid input");
                scanner.next();
            }
        }
    }
}