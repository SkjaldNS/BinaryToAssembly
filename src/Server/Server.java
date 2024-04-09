

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{
    public static void main(String[] args) {
        Server server = new Server();
        server.run();

    }
    @Override
    public void run() {
        System.out.println("Server started");
        try {
            ServerSocket serverSocket = new ServerSocket(9427);
            while(true) {
                System.out.println("Waiting for connection...");
                Socket socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String message = reader.readLine();
                System.out.println("Received: " + message);
                String data = message.substring(2);
                System.out.println(data);
                String converted = Binary.convertBinaryToAssembly(data);
                System.out.println(converted);
                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);
                writer.println(converted);
                output.flush();
                output.close();
                socket.close();
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error starting server");
            System.exit(1);
        }
    }
}
