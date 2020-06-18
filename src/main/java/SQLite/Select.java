package SQLite;

import java.sql.Statement;
import java.sql.*;

public class Select {

    public synchronized static String CheckEntry(String nameDB, String sql_command) {
        Connection conn = Connect.connect(nameDB);
        ResultSet rs = null;
        String result = "";
        try {
            PreparedStatement pstmt  = conn.prepareStatement(sql_command);
            rs = pstmt.executeQuery();

            if(rs.next()) {

                return "1";
            }
            else
                return "0";


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Connect.closeConnection();
            try {

                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public  static String IPAddress(String nameDB, String sql_command) {
        Connection conn = Connect.connect(nameDB);
        String ip = "";
        try {
            PreparedStatement pstmt  = conn.prepareStatement(sql_command);
            ResultSet rs = pstmt.executeQuery();

           if(rs.next()) {
                ip = rs.getString("IP_ADDRESS");
                System.out.println(ip);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Connect.closeConnection();
        }

        return ip;
    }

    public synchronized static String[] getRow(String nameDB, String sql_command) {
        Connection conn = Connect.connect(nameDB);
        Statement stmt = null;
        ResultSet rs = null;
        String ip[] = new String[3];
        try {
            stmt  = conn.createStatement();
            rs = stmt.executeQuery(sql_command);

            if(rs.next() && rs!=null) {
                ip[0] = rs.getString("USERNAME");
                System.out.println(ip[0]);
                ip[1] = rs.getString("HELP_MESSAGE");
                System.out.println(ip[1]);
                ip[2] = rs.getString("IP_ADDRESS");
                System.out.println(ip[2]);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            Connect.closeConnection();
        }

        return ip;
    }


}
