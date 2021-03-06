package Network;
/*
import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BroadcastClient {
    public static DatagramSocket c = null;
    private static String message;


    public static void run(String message) {
        try {
            c = new DatagramSocket();
            c.setBroadcast(true);
            System.out.println("SENDING MESSAGE");
            byte[] sendData = message.getBytes();

            try {
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("192.168.1.255"), 54123);
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
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, 54123);
                        c.send(sendPacket);
                        System.out.println("PACKET SENT");
                    } catch (Exception e) {
                    }
                }
            }
            byte[] recvBuf = new byte[15000];
          //  DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
       //     c.receive(receivePacket);
            c.close();
            System.out.println("MESSAGE HAS BEEN SENT");

        } catch (IOException ex) {
          //  Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return;
        }
    }



}
*/
import java.net.DatagramPacket;
        import java.net.DatagramSocket;
        import java.net.InetAddress;

public class BroadcastClient {

    public static void run(String message) {

        try {

            DatagramSocket socket = new DatagramSocket();
            socket.setBroadcast(true);

            byte[] buffer = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("255.255.255.255"), 61000);
            socket.send(packet);

        }catch(Exception ex) {
            ex.printStackTrace();
        }

    }

}