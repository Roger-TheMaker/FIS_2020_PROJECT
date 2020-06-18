package SQLite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Delete {

    public static void DeleteEntry(String nameDB, String tableName,String sql_command  ) {
        Connection conn = Connect.connect(nameDB);
        try {
            PreparedStatement pstmt  = conn.prepareStatement(sql_command);
            pstmt.executeUpdate();
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            Connect.closeConnection();
        }
    }

}
