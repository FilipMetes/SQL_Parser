package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class WalletConnect {
    private String user;
    private String password;
    private String walletPath;
    private String tnsAlias;
    private String url;

    public WalletConnect(String user, String password, String walletPath, String tnsAlias) {
        this.user = user;
        this.password = password;
        this.walletPath = walletPath;
        this. tnsAlias = tnsAlias;

        this.url = "jdbc:oracle:thin:@" + this.tnsAlias + "?TNS_ADMIN=" + this.walletPath;
    }

    public boolean connect() {
        try (Connection conn = DriverManager.getConnection(this.url, this.user, this.password)) {

            System.out.println("Connected successfully!");

            try (Statement stmt = conn.createStatement()) {

                String sql = "SELECT meno, priezvisko FROM osoba";
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    String meno = rs.getString("meno");
                    String priezvisko = rs.getString("priezvisko");
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

