package database;

import javafx.scene.control.TextInputControl;

import java.sql.*;
import java.util.HashMap;

public class NonWalletConnect {
    private String host;
    private String port;
    private String sid;
    private String user;
    private String password;
    private String url;
    private HashMap<String, TextInputControl> data;

    public NonWalletConnect(HashMap<String, TextInputControl> data) {
        this.data = data;

        this.host = data.get("inputHost").getText();
        this.port = data.get("inputPort").getText();
        this.sid = data.get("inputSid").getText();
        this.user = data.get("inputUser").getText();
        this.password = data.get("inputPassword").getText();

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
