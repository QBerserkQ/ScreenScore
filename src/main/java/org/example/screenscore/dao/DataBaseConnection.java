package org.example.screenscore.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private static final String urlDb = "jdbc:sqlite:review.db";

    private static final String sql = "CREATE TABLE IF NOT EXISTS Reviews ("
            + "	id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "	rating INTEGER NOT NULL,"
            + "	title TEXT NOT NULL,"
            + "	description TEXT,"
            + "	type TEXT NOT NULL,"
            + "	image_url TEXT"
            + ");";

    static  {
        try (var conn = getConnection();
             var stmt = conn.createStatement())
        {
            stmt.execute(sql);
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(urlDb);
    }
}
