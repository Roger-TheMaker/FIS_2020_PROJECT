package SQLite;

import java.sql.Statement;
import java.sql.*;

public class Select {

    public static String CheckEntry(String nameDB, String sql_command) {
        Connection conn = Connect.connect(nameDB);
        String result = "";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql_command);

            if(rs.next())
                return "1";
            else
                return "0";

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
