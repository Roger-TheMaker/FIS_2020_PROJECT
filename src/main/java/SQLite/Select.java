package SQLite;

import java.sql.Statement;
import java.sql.*;

public class Select {

    public static String CheckEntry(String nameDB, String sql_command) {
        Connection conn = Connect.connect(nameDB);
        String result = "";
        try {
            PreparedStatement pstmt  = conn.prepareStatement(sql_command);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {

                return "1";
            }
            else
                return "0";

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
