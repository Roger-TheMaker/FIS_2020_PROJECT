package Network;

public interface Generic {

   public void sendMessage(String message);
   public String receiveMessage();
   public void closeConnection();
   public void closeSocket();
}
