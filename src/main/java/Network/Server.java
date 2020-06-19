package Network;

import Interface.ChatInterface;
import Interface.ChatInterface_V2;

import java.net.*;
import java.io.*;

public class Server {
    private static Socket socket = null;
    private static ServerSocket server = null;
    private static DataInputStream input = null;
    private static DataOutputStream out = null;
    private static int connectionStatus = 0;
    private static int port = 55666;

    public synchronized static void run() {

        try{
            server = new ServerSocket(port);
            System.out.println("WAITING FOR CLIENT");
            socket = server.accept();
            input = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            System.out.println("CONNECTED");
            ChatInterface_V2.Chat();

        }catch(Exception e){System.out.println(e);
        } finally {
            System.out.println("EXITED BRO");
        }
    }

    public static void closeConnection() {
        try {
            input.close();
            out.close();
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void closeSocket() {
        try {

            server.close();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static String receiveMessage() {
        try {
            return(String)input.readUTF();

        } catch(EOFException b) {
            System.out.println("LOL");
        }catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



} 