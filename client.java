import java.io.*;
import java.net.*;

public class client{
    public static void main(String[] args) 
    {
        // for starting the client side using constructor
         new ChatClient2();
    
}
}
class ChatClient2
 {
    BufferedReader input;
    PrintWriter output;
    BufferedReader consoleInput;
    Socket socket;
    ChatClient2()
    {
        try
        {
            // accept the connection from server
             socket = new Socket("localhost", 12345);
            System.out.println("Connected to the server!");

            // for reading the data from server and writing to server
           input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             output = new PrintWriter(socket.getOutputStream(), true);
             consoleInput = new BufferedReader(new InputStreamReader(System.in));

             // calling the startreading and startwriting methods
          startreading();
          startwriting();
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }

    }
    public void startreading()
    {
        System.out.println("enter exit to terminate the chat");

        System.out.println("reader started");
        //used lambda expression for creating the thread for reading the data from server
        Runnable r1 =()->{

            while (true)
             {
                String msg;
                try 
                {
                    msg = input.readLine();
               
                if(msg.equalsIgnoreCase("exit"))
                {
                    System.out.println("server terminated chat");
                    socket.close();
                    break;
                }
                System.out.println("------------------------------------");
                System.out.println("=>Server:"+msg);
                System.out.println("-------------------------------------");
               
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

        //used lambda expression for creating the thread for writing the data to server
        Runnable r2 =()->{
            System.out.println("writer started");
            System.out.println();

            while (true) 
            {
                String msg;
                try 
                {
                    msg = consoleInput.readLine();
               
                if(msg.equalsIgnoreCase("exit"))
                {
                    System.out.println("Clent terminited the chat");
                    socket.close();
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
        // starting the thread
        new Thread(r2).start();
    }
}