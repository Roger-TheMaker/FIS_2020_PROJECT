
// Java implementation of  Server side 
// It contains two classes : Server and ClientHandler 
// Save file as Server.java 
package Network;
import Interface.ChatInterface;
import Interface.ChatInterface_V2;
import Interface.UserService;

import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;

// Server class 
public class Server
{
    private static Vector<ClientHandler> ClientVector = new Vector<ClientHandler>();
    private static Vector<ChatInterface_V2> ChatInterface = new Vector<ChatInterface_V2>();
    private static Vector<Integer> client_id = new Vector<Integer>();
    private static Socket s;
    private static ServerSocket ss;
    private static DataInputStream dis;
    private static DataOutputStream dos;

    public static Vector<ClientHandler> getClientList() {
        return ClientVector;
    }
    public static Vector<ChatInterface_V2> getChatInterface() { return ChatInterface; }
    public static Vector<Integer> getClient_id() { return client_id; }

    public static void startServer() throws IOException
    {
        // server is listening on port 5056 
        ServerSocket ss = new ServerSocket(UserService.port_number);
        int i = 0;
        // running infinite loop for getting 
        // client request 
        while (true)
        {
            Socket s = null;

            try
            {
                // socket object to receive incoming client requests 
                s = ss.accept();

                System.out.println("A new client is connected : " + s);

                // obtaining input and out streams 
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("Assigning new thread for this client");

                // Create a new handler object for handling this request.
                ClientHandler mtch = new ClientHandler(s, dis, dos,i);

                //Chat interface ...

                // Create a new Thread with this object.
                Thread t = new Thread(mtch);


                System.out.println("Adding this client to active client list");

                // add this client to active clients list
                ClientVector.add(mtch);
             //   ChatInterface.add(chat);
                client_id.add(i);
                // start the thread.
                t.start();
                i++;
            }
            catch (Exception e){
                s.close();
                e.printStackTrace();
            }
        }
    }

    public static void serverClose() {
        try {
         //   s.close();
            ss.close();
            dis.close();
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("SERVER HAS BEEN CLOSE");
        }

    }

}

