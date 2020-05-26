package SQLite;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
public class Update {

    public static void update(String nameDB) {
        Connection conn = Connect.connect(nameDB);

        String sql_command = "UPDATE POSTS SET HELP_MESSAGE = HELP_MESSAGE";
        //fara succes, nu se poate adauga text prin intermediul sql la un column deja existent, e imposibil
        //metoda concat nu merge si nici prin +


        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql_command);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            Connect.closeConnection();
        }
    }
}
