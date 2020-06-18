package Network;

import Interface.UserInterface;
import Interface.UserService;
import Network.DiscoveryThread;
import SQLite.CreateTable;
import SQLite.Delete;
import SQLite.Insert;
//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BroadcastServer implements Runnable {
    private static DatagramSocket socket;
    public static String message;
    @Override
    public void run() {
        try {
            socket = new DatagramSocket(8888, InetAddress.getByName("0.0.0.0"));
            socket.setBroadcast(true);

            while (true) {
                byte[] recvBuf = new byte[15000];
                DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
                socket.receive(packet);



                message = new String(packet.getData()).trim();
                System.out.println(getClass().getName() + ">>>Packet received; data: " + new String(message));
                if(message.contains("MESSAGE")) {
                    message = message.replaceAll("MESSAGE", "");
                    String tableContent = "id integer PRIMARY KEY, USERNAME text NOT NULL, HELP_MESSAGE text NOT NULL, IP_ADDRESS text NOT NULL, UNIQUE(HELP_MESSAGE)";
                    CreateTable.CreateTable("test.db", "POSTS", tableContent);

                    String parameterList = "USERNAME, HELP_MESSAGE, IP_ADDRESS";
                    Insert.Insert("test.db", "POSTS", parameterList, message);
                }
                else if(message.contains("DELETE")) {
                    message = message.replaceAll("DELETE","");
                    String sql_command = "DELETE FROM POSTS WHERE USERNAME = " + "\'"+ message +"\'";
                    Delete.DeleteEntry("test.db","POSTS",sql_command);
                    System.out.println("POST DELETED");
                    UserInterface.RowID = 1;
                }
                System.out.println(getClass().getName() + ">>>Packet received; data: " + new String(message));
            }
        } catch (IOException ex) {
            Logger.getLogger(DiscoveryThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
