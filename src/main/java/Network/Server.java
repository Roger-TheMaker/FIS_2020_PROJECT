package Network;

import Interface.ChatInterface;

import java.net.*;
import java.io.*;

public class Server implements Runnable, Generic{
    private static Socket socket = null;
    private static ServerSocket server = null;
    private static DataInputStream input = null;
    private static DataOutputStream out = null;
    private static int connectionStatus = 0;
    private static int port = 6666;

    public void run() {

        try{
            server = new ServerSocket(port);
            System.out.println("WAITING FOR CLIENT");
            socket = server.accept();
            input = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            System.out.println("CONNECTED");
            ChatInterface.ChatBox(this);

        }catch(Exception e){System.out.println(e);
        }
    }

    public void closeConnection() {
        try {
            input.close();
            out.close();
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void closeSocket() {
        try {

            server.close();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public String receiveMessage() {
        try {
            return(String)input.readUTF();

        } catch(EOFException b) {
            System.out.println("LOL");
        }catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



} 