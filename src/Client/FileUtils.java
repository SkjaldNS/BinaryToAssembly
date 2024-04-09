

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FileUtils {

    static String[] loadFile(String filename) throws IOException {
        String resultSet = "";
        String[] result;
        File file = new java.io.File(filename);
        if(file.exists()) {
            try {
                Scanner scanner = new java.util.Scanner(file);
                while (scanner.hasNextLine()) {
                    resultSet += scanner.nextLine() + "\n";
                }
                result = resultSet.split("\n");
            }
            catch (Exception e) {
                throw new IOException("Error reading file");
            }

        } else {
            throw new IOException("File not found");
        }
        return result;
    }

    static void saveFile(String filename, String[] data) throws IOException {
        File file = new java.io.File(filename);
        try {
            java.io.PrintWriter writer = new java.io.PrintWriter(file);
            for (String line : data) {
                writer.println(line);
            }
            writer.close();
        }
        catch (Exception e) {
            throw new IOException("Error writing file");
        }
    }

}
