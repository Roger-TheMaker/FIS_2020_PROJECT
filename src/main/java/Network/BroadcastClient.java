package Network;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BroadcastClient {
    public static DatagramSocket c = null;

    public static void sendBroadcast(String message) {
        try {
            c = new DatagramSocket();
            c.setBroadcast(true);

            byte[] sendData = message.getBytes();

            try {
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("192.168.1.255"), 8888);
                c.send(sendPacket);
            } catch (Exception e) {
            }
            Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = (NetworkInterface) interfaces.nextElement();

                if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue;
                }

                for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                    InetAddress broadcast = interfaceAddress.getBroadcast();
                    if (broadcast == null) {
                        continue;
                    }

                    try {
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, 8888);
                        c.send(sendPacket);
                        System.out.println("PACKET SENT");
                    } catch (Exception e) {
                    }
                }
            }
            byte[] recvBuf = new byte[15000];
            DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
            c.receive(receivePacket);
            c.close();

        } catch (IOException ex) {
          //  Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
