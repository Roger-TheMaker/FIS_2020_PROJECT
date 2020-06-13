package Network;

import Interface.UserService;
import Network.DiscoveryThread;
import SQLite.CreateTable;
import SQLite.Insert;
//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BroadcastServer implements Runnable {
    private static DatagramSocket socket;

    @Override
    public void run() {
        try {
            socket = new DatagramSocket(8888, InetAddress.getByName("0.0.0.0"));
            socket.setBroadcast(true);

            while (true) {
                byte[] recvBuf = new byte[15000];
                DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
                socket.receive(packet);

                System.out.println(getClass().getName() + ">>>Packet received; data: " + new String(packet.getData()));

                String message = new String(packet.getData()).trim();

                String tableContent = "id integer PRIMARY KEY, USERNAME text NOT NULL, HELP_MESSAGE text NOT NULL, IP_ADDRESS text NOT NULL, UNIQUE(USERNAME, HELP_MESSAGE) ";
                CreateTable.CreateTable("test.db","POSTS",tableContent);

                String parameterList = "USERNAME, HELP_MESSAGE, IP_ADDRESS";
                Insert.Insert("test.db","POSTS",parameterList,message);

            }
        } catch (IOException ex) {
            Logger.getLogger(DiscoveryThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
