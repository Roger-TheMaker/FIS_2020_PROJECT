import Encryption.MD5;
import Interface.ChatInterface;
import Interface.LoginPage;
import Interface.RegistrationPage;
import Interface.UserInterface;
import Network.Client;
import Network.GetMyIP;
import Network.GetMyIPLocal;
import Network.Server;
import SQLite.CreateTable;


public class Main {

    public static void main(String[] args) {
       LoginPage.Login();

        if( LoginPage.getLoginStatus() == 1 ) {
            Server server = new Server();
            Thread thread = new Thread(server);
            thread.start();
            UserInterface.UserInterface();
        }
    }
}
