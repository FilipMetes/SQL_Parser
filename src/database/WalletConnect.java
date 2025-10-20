package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class WalletConnect {
    private String user;
    private String password;
    private String walletPath; // or /opt/oracle/wallet_mydb
    private String tnsAlias;


    public WalletConnect(String user, String password, String walletPath, String tnsAlias) {

        this.user = user;
        this.password = password;
        this.walletPath = walletPath;
        this. tnsAlias = tnsAlias;

        String url = "jdbc:oracle:thin:@" + this.tnsAlias + "?TNS_ADMIN=" + this.walletPath;
        try (Connection conn = DriverManager.getConnection(url, this.user, this.password)) {

            System.out.println("✅ Connected successfully! Welcome!!");

            // Create a Statement object
            try (Statement stmt = conn.createStatement()) {

                // Execute query
                String sql = "SELECT meno, priezvisko FROM osoba";
                ResultSet rs = stmt.executeQuery(sql);

                // Loop through results
                while (rs.next()) {
                    String meno = rs.getString("meno");
                    String priezvisko = rs.getString("priezvisko");
                    System.out.println(meno + " " + priezvisko);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

