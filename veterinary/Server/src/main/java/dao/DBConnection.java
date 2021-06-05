package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
    private static DBConnection dbConnection = null;
    private static Connection conn = null;

    private DBConnection() {

    }

    public static DBConnection getInstance() {
        if (dbConnection == null)
            dbConnection = new DBConnection();
        return dbConnection;
    }

    public void connectToDb() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/veterinary", "root", "Cristiana16");

        } catch (ClassNotFoundException e) {
            Logger logger = Logger.getLogger(DBConnection.class.getName());
            logger.log(Level.INFO,"exception message");
        }
    }

    public Connection getConnection() {
        return conn;
    }

    public void disconnect() {
        try {
            conn.close();
        } catch (SQLException throwables) {
            Logger logger = Logger.getLogger(DBConnection.class.getName());
            logger.log(Level.INFO,"exception message");
        }
    }
}
