package Network;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Server {

    public void idk() {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress("google.com", 80));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(socket.getLocalAddress());
    }

}
