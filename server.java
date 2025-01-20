import java.io.*;
import java.net.*;

public class server
{
    public static void main(String[] args) {

        // for starting the server side using constructor
        new ChatServer2();
    }
}
class ChatServer2 {

    BufferedReader input;
    PrintWriter output;
    BufferedReader consoleInput;
    ServerSocket serverSocket;
    Socket socket;
    
    ChatServer2() 
     {
        try{
            //sending the connection request to the client
             serverSocket = new ServerSocket(12345);
            System.out.println("Server is running and waiting for a client...");

             socket = serverSocket.accept();
            System.out.println("Client connected!");

            // for reading the data from client and writing to client
             input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             output = new PrintWriter(socket.getOutputStream(), true);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        consoleInput = new BufferedReader(new InputStreamReader(System.in));

        // calling the startreading and startwriting methods
        startreading();
        startwriting();
    }
    public void startreading()
    {
        //used lambda expression for creating the thread for reading the data from client
        Runnable r1=()->{

            System.out.println("reader strted");
            System.out.println("Type 'exit' to terminate the chat");
            System.out.println();
            while (true)
             {
               try
                {
                String msg=input.readLine();
                if(msg.equalsIgnoreCase("exit"))
                {
                    System.out.println("client temeneted chat");
                    break;
                }
               System.out.println("-----------------------------------");
                System.out.println("=>Client:"+msg);
                System.out.println("------------------------------------");
                

            }
             catch (IOException e) 
             {
                e.printStackTrace();
             }
            }
        };
        // starting the thread
        new Thread(r1).start();
        
    }
    public void startwriting()
    {
        //used lambda expression for creating the thread for writing the data to client
        System.out.println("writer started");
       
        Runnable r2 =()->{
            while (true)
             {
            
           try {

            String msg= consoleInput.readLine();
            if(msg.equalsIgnoreCase("exit"))
            {
                System.out.println("server terminated the chat");
                break;
            }
            output.println(msg);
            output.flush();

        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
        };
    
        //starting the thread
        new Thread(r2).start();

    }

}