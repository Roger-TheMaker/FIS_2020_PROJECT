package SQLite;

import Exceptions.Empty_String;

import java.sql.*;

public class Insert {

    public synchronized static void Insert(String nameDB,String tableName, String parameterList, String values) throws Empty_String {
        Connection conn = Connect.connect(nameDB);

        if(tableName.isEmpty() || parameterList.isEmpty() || values.isEmpty())
            throw  new Empty_String();

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
