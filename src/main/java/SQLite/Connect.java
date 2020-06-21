

package SQLite;

import Exceptions.Not_A_DB_File;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    private static Connection conn = null;

    public synchronized static Connection connect(String nameDB) throws  Not_A_DB_File {

        if(!nameDB.endsWith(".db")){

            throw  new Not_A_DB_File();
        }


        try {
            // db parameters
            String url = "jdbc:sqlite:" + nameDB;
            // create a connection to the database
            conn = DriverManager.getConnection(url);


           // System.out.println("Connection to SQLite has been established.");

            return conn;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public synchronized static void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}