package fr.sn4kdev.tokens.database;

import fr.sn4kdev.tokens.MainClass;

import java.sql.*;
import java.util.logging.Level;

public class MySQL {

    private String host;
    private int port;

    private String database;
    private String username;
    private String password;

    private static Connection con;

    public MySQL(String host, int port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        connect();
    }

    public void connect() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false&autoReconnect=true&useUnicode=yes&characterEncoding=UTF-8", username, password);
        } catch (SQLException e) {
            MainClass.getPlugin().log.log(Level.SEVERE, e.getMessage());
        }
    }

    public void disconnect() {
        if (isConnected()) {
            try {
                con.close();
            } catch (SQLException e) {
                MainClass.getPlugin().log.log(Level.SEVERE, e.getMessage());
            }
        }
    }

    public boolean isConnected() {
        return (con == null ? false : true);
    }

    public static Connection getConnection() {
        return con;
    }

}