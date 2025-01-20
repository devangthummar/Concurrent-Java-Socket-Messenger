import java.io.*;
import java.net.*;

public class server {
    public static void main(String[] args) {
        new ChatServer2();
    }
}

class ChatServer2 {
    BufferedReader input;
    PrintWriter output;
    BufferedReader consoleInput;
    ServerSocket serverSocket;
    Socket socket;

    ChatServer2() {
        try {
            serverSocket = new ServerSocket(12345);
            System.out.println("Server is running and waiting for a client...");
            socket = serverSocket.accept();
            System.out.println("Client connected!");

            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            consoleInput = new BufferedReader(new InputStreamReader(System.in));

            startreading();
            startwriting();
        } catch (Exception e) {
            System.out.println("Connection closed.");
        }
    }

    public void startreading() {
        Runnable r1 = () -> {
            System.out.println("Reader started. Type 'exit' to terminate.");
            try {
                while (!socket.isClosed()) {
                    String msg = input.readLine();
                    if (msg == null || msg.equalsIgnoreCase("exit")) {
                        System.out.println("Client terminated the chat.");
                        socket.close();
                        break;
                    }
                    System.out.println("\n=>Client: " + msg);
                }
            } catch (Exception e) {
                System.out.println("Reading stopped (Connection closed).");
            }
        };
        new Thread(r1).start();
    }

    public void startwriting() {
        Runnable r2 = () -> {
            System.out.println("Writer started.");
            try {
                while (!socket.isClosed()) {
                    String msg = consoleInput.readLine();
                    output.println(msg);
                    output.flush();

                    if (msg.equalsIgnoreCase("exit")) {
                        System.out.println("Server terminated the chat.");
                        socket.close();
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Writing stopped (Connection closed).");
            }
        };
        new Thread(r2).start();
    }
}