package SQLite;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Delete {

    public static void delete(String nameDB, String item_for_deletion){
        Connection conn = Connect.connect(nameDB);
        String sql_command = "DELETE FROM POSTS WHERE HELP_MESSAGE = " + item_for_deletion;

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
