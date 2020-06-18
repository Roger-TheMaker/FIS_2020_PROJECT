package SQLite;

import java.sql.*;

public class Insert {

    public static void Insert(String nameDB,String tableName, String parameterList, String values) {
        Connection conn = Connect.connect(nameDB);
        String sql_command = "INSERT INTO " + tableName + " (" + parameterList + ") " + "VALUES " + "(" + values + " ); ";

        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql_command);
            stmt.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            Connect.closeConnection();
        }


    }

}
