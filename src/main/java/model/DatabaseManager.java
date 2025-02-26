package model;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DatabaseManager {
    private final static String DB_PATH = Paths.get("src", "main", "resources", "database", "biorhythm.db").toString();
    private final static String DB_URL = "jdbc:sqlite:" + DB_PATH;

    static Connection connect() {
	try {
	    return DriverManager.getConnection(DB_URL);
	} catch (SQLException e) {
	    System.out.println("SQLite database connection failed");
	    return null;
	}
    }
}
