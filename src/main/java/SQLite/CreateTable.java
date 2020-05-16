package SQLite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {

    public static void CreateTable(String nameDB, String tableName,String parameterList) {
        Connection conn = Connect.connect(nameDB);
        String sql_command = "CREATE TABLE IF NOT EXISTS " + tableName + " (" + parameterList + ");";

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql_command);
            System.out.println("TABLE CREATED");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {

            Connect.closeConnection();
        }

    }

}
