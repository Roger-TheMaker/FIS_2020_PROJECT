package Network;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class GetMyIPLocal {

    public static String getMyIPLocal() {
        InetAddress IP= null;
        try {
            IP = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return IP.getHostAddress();
    }
}
