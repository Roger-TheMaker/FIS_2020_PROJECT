package Network;

import Interface.ChatInterface;

import java.net.*;
import java.io.*;

public class Client implements Generic {
    private static Socket socket = null;
    private static ServerSocket server = null;
    private static DataInputStream input = null;
    private static DataOutputStream out = null;
    private static int connectionStatus = 0;
    private static int port = 6666;

    public void connect(String address, int port) {
        // establish a connection
        try{
            socket = new Socket(address,port);
            out = new DataOutputStream(socket.getOutputStream());
            input = new DataInputStream(socket.getInputStream());
            System.out.println("CONNECTED");

        }catch(Exception e){System.out.println(e);
        }

    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeSocket() {
        try {
            socket.close();
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

    public void closeConnection() {
        try {

            out.close();
            input.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}