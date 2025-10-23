package database;

import java.sql.*;

public class NonWalletConnect {
    private String host;
    private String port;
    private String sid;
    private String user;
    private String password;
    private String url;

    public NonWalletConnect(String host, String port, String sid, String user, String password) {
        this.host = host;
        this.port = port;
        this.sid = sid;
        this.user = user;
        this.password = password;

        this.url = "jdbc:oracle:thin:@" + this.host + ":" + this.port + ":" + this.sid;
    }

    public boolean connect() {
        try {
            Connection conn = DriverManager.getConnection(this.url, this.user, this.password);
            System.out.println("Connected successfully!");

            try (Statement stmt = conn.createStatement()) {

                String sql = "SELECT nazov_je, dostupnost FROM jed_list";
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    String meno = rs.getString("nazov_je");
                    String priezvisko = rs.getString("dostupnost");
                    System.out.println(meno + " " + priezvisko);
                }
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            return false;
        }
    }

}
