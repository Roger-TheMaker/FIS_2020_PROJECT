import Encryption.MD5;
import Interface.*;
import Network.*;
import SQLite.CreateTable;
import SQLite.Select;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Vector;


public class Main {

    public static void main(String[] args) {
    LoginPage.Login();

        if( LoginPage.getLoginStatus() == 1 ) {



            BroadcastServer broadcastServer = new BroadcastServer();
            Thread thread_broadcastPost = new Thread(broadcastServer);
            thread_broadcastPost.start();



            UserInterface.UserInterface();
        }


}}

