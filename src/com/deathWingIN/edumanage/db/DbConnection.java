package com.deathWingIN.edumanage.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class implements the Singleton pattern to manage database connections.
 * It ensures that only one instance of the database connection is created and provides a global point of access to it.
 */
public final class DbConnection {

    // Volatile variable to ensure the instance is not cached in a thread-local manner.
    private static volatile DbConnection dbConnection;

    private Connection connection;

    /**
     * Private constructor to prevent instantiation from outside the class.
     * It initializes the database connection.
     * @throws ClassNotFoundException if the database driver class is not found.
     * @throws SQLException if a database access error occurs or the url is null.
     */
    private DbConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lms", "root", "1234");
    }

    /**
     * Provides access to the Singleton instance of the database connection.
     * Uses double-checked locking for thread-safe lazy initialization.
     * @return The singleton instance of DbConnection.
     * @throws SQLException if a database access error occurs or the url is null.
     * @throws ClassNotFoundException if the database driver class is not found.
     */
    public static DbConnection getInstance() throws SQLException, ClassNotFoundException {
        if (dbConnection == null) {
            synchronized (DbConnection.class) {
                if (dbConnection == null) {
                    dbConnection = new DbConnection();
                }
            }
        }
        return dbConnection;
    }

    /**
     * Gets the database connection.
     * @return The database connection.
     */
    public Connection getConnection() {
        return connection;
    }
}