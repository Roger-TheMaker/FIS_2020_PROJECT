package Network;

import Interface.ChatInterface_V2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

// ClientHandler class
public class ClientHandler extends Thread
{
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
    String received = "";
    String toreturn = "";
    private int client_id;
    // Constructor
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos,int client_id)
    {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        this.client_id = client_id;

    }

    public String getReceived() {
        return received;
    }

    public void setToreturn(String message) {
        toreturn = toreturn;
    }

    @Override
    public void run()
    {
     //   String received;
      //  String toreturn;
        ChatInterface_V2 chat = new ChatInterface_V2();
        Thread chatInterface = new Thread(chat);
        chatInterface.start();
        while (true)
        {
            try {

                received = dis.readUTF();
                System.out.println(received);

                if(received.equals("Exit"))
                {
                    System.out.println("Client " + this.s + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.s.close();
                    System.out.println("Connection closed");
                    break;
                }

                chat.setConversationMessage(received+"\n");


                toreturn = chat.getMessage();
                if(!toreturn.equals("")) {
                    dos.writeUTF(toreturn);
                    chat.setConversationMessage(toreturn+"\n");
                }
                toreturn = "";



            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try
        {
            // closing resources
            this.dis.close();
            this.dos.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            this.dis.close();
            this.dos.close();
            this.s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
