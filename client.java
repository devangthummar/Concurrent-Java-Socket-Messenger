import java.io.*;
import java.net.*;

public class client {
    public static void main(String[] args) {
        new ChatClient2();
    }
}

class ChatClient2 {
    BufferedReader input;
    PrintWriter output;
    BufferedReader consoleInput;
    Socket socket;

    ChatClient2() {
        try {
            socket = new Socket("localhost", 12345);
            System.out.println("Connected to the server!");

            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            consoleInput = new BufferedReader(new InputStreamReader(System.in));

            startreading();
            startwriting();
        } catch (Exception e) {
            System.out.println("Could not connect to server.");
        }
    }

    public void startreading() {
        Runnable r1 = () -> {
            System.out.println("Reader started. Type 'exit' to terminate.");
            try {
                while (!socket.isClosed()) {
                    String msg = input.readLine();
                    if (msg == null || msg.equalsIgnoreCase("exit")) {
                        System.out.println("Server terminated the chat.");
                        socket.close();
                        break;
                    }
                    System.out.println("\n=>Server: " + msg);
                }
            } catch (Exception e) {
                System.out.println("Reading stopped (Connection closed).");
            }
        };
        new Thread(r1).start();
    }

    public void startwriting() {
        Runnable r2 = () -> {
            try {
                while (!socket.isClosed()) {
                    String msg = consoleInput.readLine();
                    output.println(msg);
                    output.flush();

                    if (msg.equalsIgnoreCase("exit")) {
                        System.out.println("Client terminated the chat.");
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