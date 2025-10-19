package database;

import java.sql.*;

public class Connect {
    private Connection conn;

    public Connect(String host, String port, String sid, String user, String password) {
        // JDBC URL using SID
        String url = "jdbc:oracle:thin:@" + host + ":" + port + ":" + sid;

        try {
            this.conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Connected successfully without wallet!");

            try (Statement stmt = this.conn.createStatement()) {

                // Execute query
                String sql = "SELECT nazov_je, dostupnost FROM jed_list";
                ResultSet rs = stmt.executeQuery(sql);

                // Loop through results
                while (rs.next()) {
                    String meno = rs.getString("nazov_je");
                    String priezvisko = rs.getString("dostupnost");
                    System.out.println(meno + " " + priezvisko);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Optional: method to close connection
    public void close() {
        try {
            if (this.conn != null && !this.conn.isClosed()) {
                this.conn.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
